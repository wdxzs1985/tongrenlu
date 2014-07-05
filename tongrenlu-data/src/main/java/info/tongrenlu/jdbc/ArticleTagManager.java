package info.tongrenlu.jdbc;

import info.tongrenlu.entity.ArticleTagEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ArticleTagManager extends ManagerSupport implements
        Manager<ArticleTagEntity, Integer> {

    @Override
    public List<ArticleTagEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public List<ArticleTagEntity> findByArticleId(final String articleId) {
        final List<ArticleTagEntity> result = new ArrayList<ArticleTagEntity>();
        final String sql = "Select " + "ARTICLE_ID      as articleId"
                           + "    ,   TAG_ID                  as tagId"
                           + "        from R_ARTICLE_TAG "
                           + "  where   "
                           + "      ARTICLE_ID = ?  "
                           + "  and DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + articleId);
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql,
                                                                       articleId);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private ArticleTagEntity mapToBean(final Map<String, Object> map) {
        return new ArticleTagEntity((String) map.get("articleId"),
                                    (String) map.get("tagId"));
    }

    @Override
    public ArticleTagEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from r_article_tag "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        final Map<String, Object> result = this.getMysqlDao().queryForMap(sql,
                                                                          id);
        final ArticleTagEntity bean = new ArticleTagEntity();
        bean.setId((Integer) result.get("id"));
        return bean;
    }

    @Override
    public void insert(final ArticleTagEntity bean) {
        final String sql = "INSERT INTO " + "r_article_tag("
                           + "article_id,"
                           + "tag_id"
                           + ") VALUES ("
                           + "?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  bean.getArticle().getId(),
                                  bean.getTag().getId());
        // bean.setId(this.findId());
    }

    @Override
    public void deleteAll() {
        this.deleteAll("r_article_tag");

    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

    @Override
    public void save(final ArticleTagEntity bean) {
        this.insert(bean);
    }

    @Override
    public void update(final ArticleTagEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

}
