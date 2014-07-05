package info.tongrenlu.jdbc;

import info.tongrenlu.entity.AccessEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class AccessManager extends ManagerSupport implements
        Manager<AccessEntity, Integer> {

    @Override
    public List<AccessEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public List<AccessEntity> findByArticleId(final String articleId) {
        final List<AccessEntity> result = new ArrayList<AccessEntity>();
        final String sql = "Select " + "ARTICLE_ID      as articleId"
                           + "    ,   User_id                  as userId"
                           + "        from M_ACCESS "
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

    private AccessEntity mapToBean(final Map<String, Object> map) {
        return new AccessEntity((String) map.get("articleId"),
                                (String) map.get("userId"));
    }

    @Override
    public AccessEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_access");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void save(final AccessEntity bean) {
        this.insert(bean);
    }

    @Override
    public void insert(final AccessEntity bean) {
        final String sql = "INSERT INTO " + "m_access ("
                           + "article_id,"
                           + "user_id"
                           + ") VALUES ("
                           + "?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  bean.getArticle().getId(),
                                  bean.getUser().getId());
        // bean.setId(this.findId());
    }

    @Override
    public void update(final AccessEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

}
