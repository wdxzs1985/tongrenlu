package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.mapper.ArticleMapper;
import info.tongrenlu.mapper.ArticleTagMapper;
import info.tongrenlu.mapper.FileMapper;
import info.tongrenlu.mapper.MusicMapper;
import info.tongrenlu.mapper.TagMapper;
import info.tongrenlu.mapper.TrackMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ArticleManager {

    public static final int TITLE_LENGTH = 100;

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private ArticleMapper articleMapper = null;
    @Autowired
    private ArticleTagMapper articleTagMapper = null;
    @Autowired
    private MusicMapper musicMapper = null;
    @Autowired
    private TagMapper tagMapper = null;
    @Autowired
    private FileMapper fileMapper = null;
    @Autowired
    private TrackMapper trackMapper = null;

    public void insert(final ArticleBean articleBean) {
        this.articleMapper.insert(articleBean);
        if (articleBean instanceof MusicBean) {
            this.musicMapper.insert((MusicBean) articleBean);
        }
    }

    public void update(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", articleBean.getId());
        param.put("title", articleBean.getTitle());
        param.put("descrption", articleBean.getDescription());
        this.articleMapper.update(param);
        if (articleBean instanceof MusicBean) {
            // this.musicMapper.update((MusicBean) articleBean);
        }
    }

    public void delete(final ArticleBean articleBean) {
        if (articleBean instanceof MusicBean) {
            this.musicMapper.delete(articleBean);
        }
    }

    public void publish(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", articleBean.getId());
        param.put("publishFlg", "1");
        this.articleMapper.update(param);
    }

    public int countMusic(final Map<String, Object> params) {
        return this.musicMapper.countForSearch(params);
    }

    public List<MusicBean> searchMusic(final Map<String, Object> params) {
        return this.musicMapper.fetchListForSearch(params);
    }

    public void addTag(final ArticleBean articleBean, final TagBean tagBean) {
        final ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setArticleBean(articleBean);
        articleTagBean.setTagBean(tagBean);
        this.articleTagMapper.insert(articleTagBean);
    }

    public List<String> getTags(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleBean", articleBean);
        return this.articleTagMapper.fetchTags(param);
    }

    public void removeTags(final ArticleBean articleBean) {
        final ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setArticleBean(articleBean);
        this.articleTagMapper.delete(articleTagBean);
    }

    public MusicBean getMusicById(final Integer id) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return this.musicMapper.fetchBean(param);
    }

    public List<FileBean> getFiles(final Integer articleId,
                                   final String contentType) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleId", articleId);
        param.put("contentType", contentType);
        return this.fileMapper.fetchList(param);
    }

    public FileBean getFile(final Integer id) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return this.fileMapper.fetchBean(param);
    }

    public void addFile(final FileBean fileBean) {
        this.fileMapper.insert(fileBean);
    }

    public void updateFile(final FileBean fileBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", fileBean.getId());
        param.put("orderNo", fileBean.getOrderNo());
        this.fileMapper.update(param);
    }

    public void deleteFile(final FileBean fileBean) {
        this.fileMapper.delete(fileBean);
    }

    public List<TrackBean> getTrackList(final Integer articleId) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleId", articleId);
        return this.trackMapper.fetchList(param);
    }

    public void addTrack(final TrackBean trackBean) {
        this.trackMapper.insert(trackBean);
    }

    public void updateTrack(final TrackBean trackBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", trackBean.getId());
        param.put("name", trackBean.getName());
        param.put("artist", trackBean.getArtist());
        param.put("original", trackBean.getOriginal());
        param.put("instrumental", trackBean.getInstrumental());
        this.trackMapper.update(param);
    }

    public void deleteTrack(final TrackBean trackBean) {
        this.trackMapper.delete(trackBean);
    }

    public boolean validateTitle(final String title,
                                 final String errorAttribute,
                                 final Map<String, Object> model,
                                 final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("ArticleBean.title",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(title)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.empty",
                                                    new Object[] { fieldName },
                                                    locale));
            isValid = false;
        } else if (StringUtils.length(title) > ArticleManager.TITLE_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                            ArticleManager.TITLE_LENGTH },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }
}