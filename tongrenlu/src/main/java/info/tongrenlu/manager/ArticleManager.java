package info.tongrenlu.manager;

import info.tongrenlu.domain.AccessBean;
import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.ArticleTagBean;
import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.AccessMapper;
import info.tongrenlu.mapper.ArticleMapper;
import info.tongrenlu.mapper.ArticleTagMapper;
import info.tongrenlu.mapper.FileMapper;
import info.tongrenlu.mapper.MusicMapper;
import info.tongrenlu.mapper.TagMapper;

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
    private AccessMapper accessMapper = null;
    @Autowired
    private ArticleTagMapper articleTagMapper = null;
    @Autowired
    private MusicMapper musicMapper = null;
    @Autowired
    private TagMapper tagMapper = null;
    @Autowired
    private FileMapper fileMapper = null;

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
        param.put("description", articleBean.getDescription());
        this.articleMapper.update(param);
        if (articleBean instanceof MusicBean) {
            // this.musicMapper.update(param);
        }
    }

    public void delete(final ArticleBean articleBean) {
        this.articleMapper.delete(articleBean);
    }

    public void publish(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", articleBean.getId());
        param.put("publishFlg", ArticleBean.PUBLISH);
        this.articleMapper.publish(param);
    }

    public void free(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", articleBean.getId());
        param.put("publishFlg", ArticleBean.FREE);
        this.articleMapper.publish(param);
    }

    public void addAccess(final ArticleBean articleBean, final UserBean userBean) {
        final AccessBean accessBean = new AccessBean();
        accessBean.setArticleBean(articleBean);
        accessBean.setUserBean(userBean);
        this.accessMapper.insert(accessBean);
        this.accessMapper.updateArticle(accessBean);

        int accessCount = articleBean.getAccessCount();
        accessCount++;
        articleBean.setAccessCount(accessCount);
    }

    public int countMusic(final Map<String, Object> params) {
        return this.musicMapper.count(params);
    }

    public List<MusicBean> searchMusic(final Map<String, Object> params) {
        return this.musicMapper.fetchList(params);
    }

    public MusicBean getMusicById(final Integer articleId) {
        final Map<String, Object> param = new HashMap<>();
        param.put("id", articleId);
        return this.musicMapper.fetchBean(param);
    }

    public List<MusicBean> getMusicRanking(final int pageSize) {
        final Map<String, Object> param = new HashMap<>();
        param.put("pageSize", pageSize);
        return this.musicMapper.fetchRanking(param);
    }

    public List<MusicBean> getMusicTopping(final int pageSize) {
        final Map<String, Object> param = new HashMap<>();
        param.put("pageSize", pageSize);
        return this.musicMapper.fetchTopping(param);
    }

    public void addTag(final ArticleBean articleBean, final TagBean tagBean) {
        final ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setArticleBean(articleBean);
        articleTagBean.setTagBean(tagBean);
        this.articleTagMapper.delete(articleTagBean);
        this.articleTagMapper.insert(articleTagBean);
    }

    public List<TagBean> getTags(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<>();
        param.put("articleBean", articleBean);
        return this.articleTagMapper.fetchList(param);
    }

    public void removeTags(final ArticleBean articleBean) {
        final ArticleTagBean articleTagBean = new ArticleTagBean();
        articleTagBean.setArticleBean(articleBean);
        this.articleTagMapper.delete(articleTagBean);
    }

    public List<FileBean> getFiles(final Integer articleId, final String contentType) {
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

    public boolean validateTitle(final String title,
                                 final String errorAttribute,
                                 final Map<String, Object> model,
                                 final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("ArticleBean.title", null, locale);
        if (StringUtils.isBlank(title)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.empty", new Object[] { fieldName }, locale));
            isValid = false;
        } else if (StringUtils.length(title) > ArticleManager.TITLE_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong", new Object[] { fieldName,
                              ArticleManager.TITLE_LENGTH }, locale));
            isValid = false;
        }
        return isValid;
    }

    public int countLuckyMusic(final Map<String, Object> params) {
        return this.musicMapper.countLucky(params);
    }

    public List<MusicBean> fetchLuckyMusic(final Map<String, Object> params) {
        return this.musicMapper.fetchLucky(params);
    }

}