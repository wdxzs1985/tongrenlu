package info.tongrenlu.service.dao;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MArticleMapper;
import info.tongrenlu.persistence.RCollectMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleDao {

    @Autowired
    private MArticleMapper articleMapper = null;
    @Autowired
    private RCollectMapper collectMapper = null;

    public ArticleBean getArticleById(final String id) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.articleMapper.fetchBean(params);
    }

    public void addAccess(final ArticleBean articleBean, final UserBean userBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("articleBean", articleBean);
        params.put("userBean", userBean);
        this.articleMapper.insertAccess(params);
        articleBean.setAccessCount(articleBean.getAccessCount() + 1);
    }

    public void publish(final ArticleBean articleBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        this.articleMapper.publish(param);
    }

    public void recommend(final ArticleBean articleBean, final String recommend) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("recommend", recommend);
        this.articleMapper.recommend(param);
    }

    public int countByUser(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        return this.articleMapper.countByUser(param);
    }

    public boolean hasCollected(final ArticleBean articleBean,
                                final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("userBean", userBean);
        final int count = this.collectMapper.count(param);
        return count > 0;
    }

    public void addCollect(final ArticleBean articleBean,
                           final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("userBean", userBean);
        this.collectMapper.insert(param);
    }

    public void removeCollect(final ArticleBean articleBean,
                              final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("userBean", userBean);
        this.collectMapper.delete(param);
    }

    public int countCollect(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        final int itemCount = this.collectMapper.count(param);
        return itemCount;
    }

}
