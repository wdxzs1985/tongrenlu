package info.tongrenlu.service;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.manager.AritcleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
    private AritcleManager aritcleManager = null;
    @Autowired
    private TagManager tagManager = null;
    @Autowired
    private FileManager fileManager = null;

    @Transactional
    public boolean doCreate(final MusicBean inputMusic,
                            final String[] tags,
                            final Map<String, Object> model,
                            final Locale locale) {
        if (this.validateForCreate(inputMusic, model, locale)) {
            this.aritcleManager.insert(inputMusic);
            for (final String tag : tags) {
                final TagBean tagBean = this.tagManager.create(tag);
                this.aritcleManager.addTag(inputMusic, tagBean);
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
            this.aritcleManager.update(musicBean);
            this.aritcleManager.removeTags(musicBean);
            for (final String tag : tags) {
                final TagBean tagBean = this.tagManager.create(tag);
                this.aritcleManager.addTag(musicBean, tagBean);
            }
            // TODO update solr document
            return true;
        }
        return false;
    }

    @Transactional
    public void doDelete(final MusicBean musicBean) {
        this.aritcleManager.delete(musicBean);
        // TODO delete solr document
        this.fileManager.deleteArticle(musicBean);
    }

    public void searchMusic(final PaginateSupport<MusicBean> paginate) {
        final int itemCount = this.aritcleManager.countMusic(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<MusicBean> items = this.aritcleManager.searchMusic(paginate.getParams());
        paginate.setItems(items);
    }

    public MusicBean getById(final Integer id) {
        return this.aritcleManager.getMusicById(id);
    }

    public List<String> getTags(final MusicBean musicBean) {
        return this.aritcleManager.getTags(musicBean);
    }

    public List<FileBean> getTrackFileList(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.aritcleManager.getFiles(articleId, FileManager.AUDIO);
    }

    public List<FileBean> getBookletFileList(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.aritcleManager.getFiles(articleId, FileManager.IMAGE);
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
            this.aritcleManager.addFile(fileBean);

            final TrackBean trackBean = new TrackBean();
            trackBean.setId(fileBean.getId());
            trackBean.setName(fileBean.getName());
            this.aritcleManager.addTrack(trackBean);
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
            this.aritcleManager.addFile(fileBean);
            this.fileManager.saveFile(FileManager.MUSIC, fileBean, upload);
            this.fileManager.convertImage(FileManager.MUSIC, fileBean);
            return true;
        }
        return false;
    }

    @Transactional
    public void removeFile(final Integer fileId) {
        final FileBean fileBean = this.aritcleManager.getFile(fileId);
        if (fileBean != null) {
            this.aritcleManager.deleteFile(fileBean);

            if (FileManager.AUDIO.equals(fileBean.getContentType())) {
                final TrackBean trackBean = new TrackBean();
                trackBean.setFileBean(fileBean);
                this.aritcleManager.deleteTrack(trackBean);
            }

            this.fileManager.deleteFile(FileManager.MUSIC, fileBean);
        }
    }

    public List<TrackBean> getTrackList(final Integer articleId) {
        return this.aritcleManager.getTrackList(articleId);
    }

    @Transactional
    public void updateTrackList(final List<TrackBean> trackList) {
        for (final TrackBean trackBean : trackList) {
            this.aritcleManager.updateTrack(trackBean);
            this.aritcleManager.updateFile(trackBean.getFileBean());
        }
    }

    @Transactional
    public void publish(final MusicBean musicBean) {
        this.aritcleManager.publish(musicBean);
    }

    private boolean validateForCreate(final MusicBean inputArticle,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        if (!this.aritcleManager.validateTitle(inputArticle.getTitle(),
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
        if (!this.aritcleManager.validateTitle(inputArticle.getTitle(),
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

}
