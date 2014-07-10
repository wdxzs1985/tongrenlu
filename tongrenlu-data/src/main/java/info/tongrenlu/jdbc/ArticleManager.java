package info.tongrenlu.jdbc;

import info.tongrenlu.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ArticleManager extends ManagerSupport implements
        Manager<ArticleEntity, Integer> {

    @Override
    public List<ArticleEntity> findAll() {
        final List<ArticleEntity> result = new ArrayList<ArticleEntity>();
        final String sql = "Select " + "ARTICLE_ID      as articleId"
                           + "    ,   TITLE                  as title"
                           + "    ,   DESCRIPTION                 as description"
                           + "    ,   USER_ID                 as userId"
                           + "    ,   PUBLISH_FLG                   as publishFlg"
                           + "    ,   PUBLISH_DATE             as publishDate"
                           + "        from M_ARTICLE "
                           + "         where DEL_FLG = '0'";
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    public ArticleEntity findByArticleId(final String articleId) {
        final String sql = "Select " + "ARTICLE_ID      as articleId"
                           + "    ,   TITLE                  as title"
                           + "    ,   DESCRIPTION                 as description"
                           + "    ,   USER_ID                 as userId"
                           + "    ,   PUBLISH_FLG                   as publishFlg"
                           + "    ,   PUBLISH_DATE             as publishDate"
                           + "        from M_ARTICLE "
                           + "  where "
                           + "      ARTICLE_ID = ?  "
                           + "  and DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + articleId);
        final Map<String, Object> result = this.getOracleDao()
                                               .queryForMap(sql, articleId);
        return this.mapToBean(result);
    }

    private ArticleEntity mapToBean(final Map<String, Object> map) {
        return new ArticleEntity((String) map.get("articleId"),
                                 (String) map.get("title"),
                                 (String) map.get("description"),
                                 (String) map.get("userId"),
                                 (String) map.get("publishFlg"),
                                 (Date) map.get("publishDate"),
                                 "0");
    }

    @Override
    public ArticleEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from m_article "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        final Map<String, Object> result = this.getMysqlDao().queryForMap(sql,
                                                                          id);
        final ArticleEntity bean = new ArticleEntity();
        bean.setId((Integer) result.get("id"));
        return bean;
    }

    @Override
    public void insert(final ArticleEntity article) {
        final String sql = "INSERT INTO " + "m_article("
                           + "user_id,"
                           + "title,"
                           + "description,"
                           + "publish_flg,"
                           + "publish_date,"
                           + "recommend_flg"
                           + ") VALUES ("
                           + "?,?,?,?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  article.getUser().getId(),
                                  article.getTitle(),
                                  article.getDescription(),
                                  article.getPublishFlg(),
                                  article.getPublishDate(),
                                  article.getRecommend());
        article.setId(this.findId());
    }

    @Override
    public void save(final ArticleEntity article) {
        this.insert(article);
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_article");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final ArticleEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public void updateAccessCnt(final Map<String, Object> map) {
        final String sql = "update " + "m_article"
                           + "        set access_cnt = ? "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[access_cnt] = " + map.get("cnt"));
        this.log.info("[id] = " + map.get("articleId"));
        this.getMysqlDao().update(sql, map.get("cnt"), map.get("articleId"));
    }
}
