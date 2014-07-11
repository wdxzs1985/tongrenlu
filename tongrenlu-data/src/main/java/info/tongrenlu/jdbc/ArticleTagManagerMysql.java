package info.tongrenlu.jdbc;

import info.tongrenlu.entity.ArticleTagEntity;
import info.tongrenlu.entity.TagEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class ArticleTagManagerMysql extends ManagerSupport implements
        Manager<ArticleTagEntity, Integer> {

    @Override
    public List<ArticleTagEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public List<ArticleTagEntity> findByArticleId(final Integer articleId) {
        final List<ArticleTagEntity> result = new ArrayList<ArticleTagEntity>();
        final String sql = "Select  " + "   m_tag.id                  as tagId"
                           + "    ,   m_tag.TAG                  as tag"
                           + "        from r_article_tag "
                           + "      join m_tag on r_article_tag.tag_id = m_tag.id "
                           + "  where   "
                           + "      r_article_tag.ARTICLE_ID = ?  "
                           + "  and r_article_tag.DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + articleId);
        final List<Map<String, Object>> resultList = this.getMysqlDao()
                                                         .queryForList(sql,
                                                                       articleId);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private ArticleTagEntity mapToBean(final Map<String, Object> map) {
        final TagEntity tag = new TagEntity();
        tag.setId((Integer) map.get("tagId"));
        tag.setTag((String) map.get("tag"));

        final ArticleTagEntity entity = new ArticleTagEntity();
        entity.setTag(tag);
        return entity;
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
