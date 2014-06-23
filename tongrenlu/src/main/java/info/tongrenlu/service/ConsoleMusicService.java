package info.tongrenlu.service;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.manager.AritcleManager;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.TagManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ConsoleMusicService {

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

    public List<FileBean> getTrackFiles(final MusicBean musicBean) {
        final Integer articleId = musicBean.getId();
        return this.aritcleManager.getFiles(articleId, "mp3");
    }

    public FileBean addTrackFile(final Integer articleId,
                                 final MultipartFile upload) {
        final FileBean fileBean = new FileBean();
        fileBean.setArticleId(articleId);
        fileBean.setName(upload.getOriginalFilename());
        fileBean.setExtension("mp3");
        this.aritcleManager.addFile(fileBean);

        final TrackBean trackBean = new TrackBean();
        trackBean.setId(fileBean.getId());
        final String name = FilenameUtils.getBaseName(fileBean.getName());
        trackBean.setName(StringUtils.left(name, 255));
        this.aritcleManager.addTrack(trackBean);

        this.fileManager.saveMp3(fileBean, upload);
        return fileBean;
    }

    public void removeTrackFile(final FileBean fileBean) {
        this.aritcleManager.deleteFile(fileBean);

        final TrackBean trackBean = new TrackBean();
        trackBean.setFileBean(fileBean);
        this.aritcleManager.deleteTrack(trackBean);

        this.fileManager.deleteFile(fileBean);
    }

    public List<TrackBean> getTrackList(final Integer articleId) {
        return this.aritcleManager.getTrackList(articleId);
    }

    @Transactional
    public void updateTrack(final List<TrackBean> trackList) {
        for (final TrackBean trackBean : trackList) {
            this.aritcleManager.updateTrack(trackBean);
            this.aritcleManager.updateFile(trackBean.getFileBean());
        }
    }

    @Transactional
    public void publish(final MusicBean musicBean) {
        this.aritcleManager.publish(musicBean);
    }

}
