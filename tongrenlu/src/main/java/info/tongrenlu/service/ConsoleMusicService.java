package info.tongrenlu.service;

import info.tongrenlu.constants.CommonConstants;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.manager.ArticleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.solr.ArticleDocument;
import info.tongrenlu.solr.ArticleRepository;
import info.tongrenlu.solr.MusicDocument;
import info.tongrenlu.solr.TrackDocument;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ConsoleMusicService {

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
    public boolean doCreate(final MusicBean inputMusic,
                            final String[] tags,
                            final Map<String, Object> model,
                            final Locale locale) {
        if (this.validateForCreate(inputMusic, model, locale)) {
            this.articleManager.insert(inputMusic);
            for (final String tag : tags) {
                final TagBean tagBean = this.tagManager.create(tag);
                this.articleManager.addTag(inputMusic, tagBean);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean doEdit(final MusicBean musicBean,
                          final String[] tags,
                          final Map<String, Object> model,
                          final Locale locale) {
        if (this.validateForEdit(musicBean, model, locale)) {
            this.articleManager.update(musicBean);
            this.articleManager.removeTags(musicBean);
            for (final String tag : tags) {
                final TagBean tagBean = this.tagManager.create(tag);
                this.articleManager.addTag(musicBean, tagBean);
            }

            if (CommonConstants.is(musicBean.getPublishFlg())) {
                this.saveMusicDocument(musicBean, tags);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public void doDelete(final MusicBean musicBean) {
        this.articleManager.delete(musicBean);
        this.deleteMusicDocument(musicBean);
        this.fileManager.deleteArticle(musicBean);
    }

    public void searchMusic(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.articleManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.articleManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public MusicBean getById(final Integer id) {
        return this.articleManager.getMusicById(id);
    }

    public String[] getTags(final MusicBean musicBean) {
        return this.articleManager.getTags(musicBean).toArray(new String[] {});
    }

    public List<FileBean> getTrackFileList(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.articleManager.getFiles(articleId, FileManager.AUDIO);
    }

    public List<FileBean> getBookletFileList(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
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
    public boolean addTrackFile(final FileBean fileBean,
                                final MultipartFile upload,
                                final Map<String, Object> fileModel,
                                final Locale locale) {
        if (this.validateForTrackFile(upload, fileModel, locale)) {
            this.articleManager.addFile(fileBean);

            final TrackBean trackBean = new TrackBean();
            trackBean.setId(fileBean.getId());
            trackBean.setName(fileBean.getName());
            this.articleManager.addTrack(trackBean);
            this.fileManager.saveFile(FileManager.MUSIC, fileBean, upload);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addBookletFile(final FileBean fileBean,
                                  final MultipartFile upload,
                                  final Map<String, Object> fileModel,
                                  final Locale locale) {
        if (this.validateForBookletFile(upload, fileModel, locale)) {
            this.articleManager.addFile(fileBean);
            this.fileManager.saveFile(FileManager.MUSIC, fileBean, upload);
            this.fileManager.convertImage(FileManager.MUSIC, fileBean);
            return true;
        }
        return false;
    }

    @Transactional
    public void removeFile(final Integer fileId) {
        final FileBean fileBean = this.articleManager.getFile(fileId);
        if (fileBean != null) {
            this.articleManager.deleteFile(fileBean);

            if (FileManager.AUDIO.equals(fileBean.getContentType())) {
                final TrackBean trackBean = new TrackBean();
                trackBean.setFileBean(fileBean);
                this.articleManager.deleteTrack(trackBean);

                this.deleteTrackDocument(trackBean);
            }

            this.fileManager.deleteFile(FileManager.MUSIC, fileBean);
        }
    }

    public List<TrackBean> getTrackList(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.articleManager.getTrackList(articleId);
    }

    @Transactional
    public void updateTrackList(final List<TrackBean> trackList,
                                final MusicBean musicBean) {
        final boolean isPublish = CommonConstants.is(musicBean.getPublishFlg());

        for (final TrackBean trackBean : trackList) {
            this.articleManager.updateTrack(trackBean);
            this.articleManager.updateFile(trackBean.getFileBean());

            if (isPublish) {
                this.saveTrackDocument(trackBean, musicBean);
            }
        }
    }

    @Transactional
    public void publish(final MusicBean musicBean) {
        this.articleManager.publish(musicBean);

        final String[] tags = this.getTags(musicBean);
        this.saveMusicDocument(musicBean, tags);

        for (final TrackBean trackBean : this.getTrackList(musicBean)) {
            this.saveTrackDocument(trackBean, musicBean);
        }
    }

    private boolean validateForCreate(final MusicBean inputArticle,
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

    private boolean validateForEdit(final MusicBean inputArticle,
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

    private boolean validateForTrackFile(final MultipartFile upload,
                                         final Map<String, Object> fileModel,
                                         final Locale locale) {
        boolean isValid = true;
        if (!this.fileManager.validateContentType(upload.getContentType(),
                                                  FileManager.ACCEPT_AUDIO,
                                                  "error",
                                                  fileModel,
                                                  locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateForBookletFile(final MultipartFile upload,
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
    public void saveMusicDocument(final MusicBean musicBean, final String[] tags) {
        final Integer articleId = musicBean.getId();
        final String id = "m" + articleId;
        ArticleDocument document = this.articleRepository.findOne(id);
        if (document == null) {
            document = new MusicDocument();
            document.setId(id);
            document.setArticleId(articleId);
        }
        document.setTitle(musicBean.getTitle());
        document.setDescription(musicBean.getDescription());
        document.setTags(tags);

        this.articleRepository.save(document);

        for (final ArticleDocument trackDocument : this.articleRepository.findTrackByArticleId(articleId)) {
            trackDocument.setTitle(musicBean.getTitle());
            this.articleRepository.save(trackDocument);
        }
    }

    @Transactional
    public void deleteMusicDocument(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        final String id = "m" + articleId;
        this.articleRepository.delete(id);

        for (final ArticleDocument trackDocument : this.articleRepository.findTrackByArticleId(articleId)) {
            this.articleRepository.delete(trackDocument);
        }
    }

    @Transactional
    public void saveTrackDocument(final TrackBean trackBean,
                                  final MusicBean musicBean) {
        final String id = "t" + trackBean.getId();
        ArticleDocument document = this.articleRepository.findOne(id);
        if (document == null) {
            document = new TrackDocument();
            document.setId(id);
            document.setFileId(trackBean.getId());
            document.setArticleId(musicBean.getId());
            document.setTitle(musicBean.getTitle());
        }

        document.setInstrumental(CommonConstants.is(trackBean.getInstrumental()));
        document.setArtist(StringUtils.split(trackBean.getArtist(), ","));
        document.setOriginal(StringUtils.split(trackBean.getOriginal(), "\n"));

        this.articleRepository.save(document);
    }

    @Transactional
    public void deleteTrackDocument(final TrackBean trackBean) {
        final String id = "t" + trackBean.getId();
        this.articleRepository.delete(id);
    }
}
