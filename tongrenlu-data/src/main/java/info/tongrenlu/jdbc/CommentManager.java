package info.tongrenlu.jdbc;

import info.tongrenlu.entity.CommentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class CommentManager extends ManagerSupport implements
        Manager<CommentEntity, Integer> {

    @Override
    public List<CommentEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public List<CommentEntity> findByArticleId(final String articleId) {
        final List<CommentEntity> result = new ArrayList<CommentEntity>();
        final String sql = "Select " + "COMMENT_ID      as commentId"
                           + "    ,     ARTICLE_ID      as articleId"
                           + "    ,     SENDER_USER_ID  as userId"
                           + "    ,     CONTENT         as content"
                           + "        from M_ARTICLE_COMMENT "
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

    private CommentEntity mapToBean(final Map<String, Object> map) {
        return new CommentEntity((String) map.get("commentId"),
                                 (String) map.get("articleId"),
                                 (String) map.get("userId"),
                                 (String) map.get("content"));
    }

    @Override
    public CommentEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from m_comment "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        final Map<String, Object> result = this.getMysqlDao().queryForMap(sql,
                                                                          id);
        final CommentEntity bean = new CommentEntity();
        bean.setId((Integer) result.get("id"));
        return bean;
    }

    @Override
    public void insert(final CommentEntity bean) {
        final String sql = "INSERT INTO " + "m_comment ("
                           + "article_id,"
                           + "user_id,"
                           + "content"
                           + ") VALUES ("
                           + "?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  bean.getArticle().getId(),
                                  bean.getUser().getId(),
                                  bean.getContent());
        bean.setId(this.findId());
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_comment");

    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

    @Override
    public void save(final CommentEntity bean) {
        this.insert(bean);
    }

    @Override
    public void update(final CommentEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

}
