package info.tongrenlu.service.dao;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MArticleMapper;
import info.tongrenlu.persistence.RCollectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleDao {

    @Autowired
    private MArticleMapper articleMapper = null;
    @Autowired
    private RCollectMapper collectMapper = null;

    public void addAccess(final ArticleBean articleBean, final UserBean userBean) {
        final ArticleBean bean = new ArticleBean();
        bean.setArticleId(articleBean.getArticleId());
        bean.setUserBean(userBean);
        this.articleMapper.insertAccess(bean);
        articleBean.setAccessCount(articleBean.getAccessCount() + 1);
    }

    public void publish(final ArticleBean articleBean) {
        this.articleMapper.publishArticle(articleBean);
    }

    public boolean hasCollected(final String userId, final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("articleId", articleId);
        final int count = this.collectMapper.countCollect(param);
        return count > 0;
    }

    public void addCollect(final String userId, final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("articleId", articleId);
        this.collectMapper.insertCollect(param);
    }

    public void removeCollect(final String userId, final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("articleId", articleId);
        this.collectMapper.deleteCollect(param);
    }

    public int countUserArticle(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        final int itemCount = this.articleMapper.getArticleCount(param);
        return itemCount;
    }

    public int countCollectArticle(final String userId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        final int itemCount = this.collectMapper.countCollect(param);
        return itemCount;
    }

    public List<ArticleBean> getArticleList(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        return this.articleMapper.getArticleList(param);
    }

    public ArticleBean getArticleBean(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        return this.articleMapper.getArticleBean(param);
    }

    public void recommendArticle(final String articleId, final String recommend) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        param.put("recommend", recommend);
        this.articleMapper.recommendArticle(param);
    }

}
