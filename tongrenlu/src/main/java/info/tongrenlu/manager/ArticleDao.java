package info.tongrenlu.manager;

import info.tongrenlu.domain.ArticleBean;
import info.tongrenlu.domain.UserBean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ArticleDao {

    // @Autowired
    // private MArticleMapper articleMapper = null;
    // @Autowired
    // private MAccessMapper accessMapper = null;
    // @Autowired
    // private RCollectMapper collectMapper = null;

    public ArticleBean getArticleById(final String id) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return null;
        // return this.articleMapper.fetchBean(params);
    }

    public void addAccess(final ArticleBean articleBean, final UserBean userBean) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("articleBean", articleBean);
        params.put("userBean", userBean);
        // this.accessMapper.insert(params);
        articleBean.setAccessCount(articleBean.getAccessCount() + 1);
    }

    public void publish(final ArticleBean articleBean) {
        // final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("articleBean", articleBean);
        // param.put("publishFlg", "1");
        // this.articleMapper.publish(param);
    }

    public void recommend(final ArticleBean articleBean, final String recommend) {
        // final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("articleBean", articleBean);
        // param.put("recommend", recommend);
        // this.articleMapper.recommend(param);
    }

    public int countByUser(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        // return this.articleMapper.countByUser(param);
        return 0;
    }

    public boolean hasCollected(final ArticleBean articleBean,
                                final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("userBean", userBean);
        // final int count = this.collectMapper.count(param);
        return false;
    }

    public void addCollect(final ArticleBean articleBean,
                           final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("userBean", userBean);
        // this.collectMapper.insert(param);
    }

    public void removeCollect(final ArticleBean articleBean,
                              final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleBean", articleBean);
        param.put("userBean", userBean);
        // this.collectMapper.delete(param);
    }

    public int countCollect(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        // final int itemCount = this.collectMapper.count(param);
        return 0;
    }

}
