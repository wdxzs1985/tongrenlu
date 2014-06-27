package info.tongrenlu.service;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.ComicDocument;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ConsoleComicService {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ArticleManager articleManager = null;
    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private FileManager fileManager = null;
    @Autowired
    private ArticleRepository articleRepository = null;

    @Transactional
    public boolean doCreate(final ComicBean inputComic,
                            final String[] tags,
                            final Map<String, Object> model,
                            final Locale locale) {
        if (this.validateForCreate(inputComic, model, locale)) {
            this.articleManager.insert(inputComic);
            if (ArrayUtils.isNotEmpty(tags)) {
                for (final String tag : tags) {
                    final TagBean tagBean = this.tagManager.create(tag);
                    this.articleManager.addTag(inputComic, tagBean);
                }
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean doEdit(final ComicBean comicBean,
                          final String[] tags,
                          final Map<String, Object> model,
                          final Locale locale) {
        if (this.validateForEdit(comicBean, model, locale)) {
            this.articleManager.update(comicBean);
            this.articleManager.removeTags(comicBean);
            if (ArrayUtils.isNotEmpty(tags)) {
                for (final String tag : tags) {
                    final TagBean tagBean = this.tagManager.create(tag);
                    this.articleManager.addTag(comicBean, tagBean);
                }
            }

            if (CommonConstants.is(comicBean.getPublishFlg())) {
                this.saveComicDocument(comicBean, tags);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public void doDelete(final ComicBean comicBean) {
        this.articleManager.delete(comicBean);
        this.deleteComicDocument(comicBean);
        this.fileManager.deleteArticle(comicBean);
    }

    public void searchComic(final PaginateSupport<ComicBean> paginate) {
        final int itemCount = this.articleManager.countComic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<ComicBean> items = this.articleManager.searchComic(paginate.getParams());
        paginate.setItems(items);
    }

    public ComicBean getById(final Integer id) {
        return this.articleManager.getComicById(id);
    }

    public String[] getTags(final ComicBean comicBean) {
        final List<TagBean> tagList = this.articleManager.getTags(comicBean);
        final List<String> tags = new ArrayList<String>();
        for (final TagBean tagBean : tagList) {
            tags.add(tagBean.getTag());
        }
        return tags.toArray(new String[] {});
    }

    public List<FileBean> getPictureFileList(final ComicBean comicBean) {
        final Integer articleId = comicBean.getId();
        return this.articleManager.getFiles(articleId, FileManager.IMAGE);
    }

    public List<Map<String, Object>> wrapFileBeanList(final List<FileBean> fileList) {
        List<Map<String, Object>> files = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            files = new ArrayList<Map<String, Object>>();
            for (final FileBean fileBean : fileList) {
                final Map<String, Object> fileModel = new HashMap<String, Object>();
                files.add(this.wrapFileBean(fileBean, fileModel));
            }
        }
        return files;
    }

    public Map<String, Object> wrapFileBean(final FileBean fileBean,
                                            final Map<String, Object> fileModel) {
        fileModel.put("id", fileBean.getId());
        fileModel.put("name", fileBean.getName());
        fileModel.put("articleId", fileBean.getArticleId());
        return fileModel;
    }

    @Transactional
    public boolean addPictureFile(final FileBean fileBean,
                                  final MultipartFile upload,
                                  final Map<String, Object> fileModel,
                                  final Locale locale) {
        if (this.validateForPictureFile(upload, fileModel, locale)) {
            this.articleManager.addFile(fileBean);
            this.fileManager.saveFile(FileManager.COMIC, fileBean, upload);
            this.fileManager.convertImage(FileManager.COMIC, fileBean);
            return true;
        }
        return false;
    }

    @Transactional
    public void removeFile(final Integer fileId) {
        final FileBean fileBean = this.articleManager.getFile(fileId);
        if (fileBean != null) {
            this.articleManager.deleteFile(fileBean);
            this.fileManager.deleteFile(FileManager.COMIC, fileBean);
        }
    }

    @Transactional
    public void publish(final ComicBean comicBean) {
        this.articleManager.publish(comicBean);
        final String[] tags = this.getTags(comicBean);
        this.saveComicDocument(comicBean, tags);
    }

    private boolean validateForCreate(final ComicBean inputArticle,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        if (!this.articleManager.validateTitle(inputArticle.getTitle(),
                                               "titleError",
                                               model,
                                               locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateForEdit(final ComicBean inputArticle,
                                    final Map<String, Object> model,
                                    final Locale locale) {
        boolean isValid = true;
        if (!this.articleManager.validateTitle(inputArticle.getTitle(),
                                               "titleError",
                                               model,
                                               locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateForPictureFile(final MultipartFile upload,
                                           final Map<String, Object> fileModel,
                                           final Locale locale) {
        boolean isValid = true;
        if (!this.fileManager.validateContentType(upload.getContentType(),
                                                  FileManager.ACCEPT_IMAGE,
                                                  "error",
                                                  fileModel,
                                                  locale)) {
            isValid = false;
        }
        return isValid;
    }

    @Transactional
    public void saveComicDocument(final ComicBean comicBean, final String[] tags) {
        final Integer articleId = comicBean.getId();
        final String id = "c" + articleId;
        ArticleDocument document = this.articleRepository.findOne(id);
        if (document == null) {
            document = new ComicDocument();
            document.setId(id);
            document.setArticleId(articleId);
        }
        document.setTitle(comicBean.getTitle());
        document.setDescription(comicBean.getDescription());
        document.setTags(tags);

        this.articleRepository.save(document);
    }

    @Transactional
    public void deleteComicDocument(final ComicBean comicBean) {
        final Integer articleId = comicBean.getId();
        final String id = "c" + articleId;
        this.articleRepository.delete(id);
    }

}
