package info.tongrenlu.jdbc;

import info.tongrenlu.entity.CollectEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class CollectManager extends ManagerSupport implements
        Manager<CollectEntity, Integer> {

    @Override
    public List<CollectEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public List<CollectEntity> findByArticleId(final String articleId) {
        final List<CollectEntity> result = new ArrayList<CollectEntity>();
        final String sql = "Select  " + "    ARTICLE_ID      as articleId"
                           + "    ,     USER_ID  as userId"
                           + "        from R_COLLECT "
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

    private CollectEntity mapToBean(final Map<String, Object> map) {
        return new CollectEntity((String) map.get("articleId"),
                                 (String) map.get("userId"));
    }

    @Override
    public CollectEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void insert(final CollectEntity bean) {
        final String sql = "INSERT INTO " + "r_like ("
                           + "like_id,"
                           + "user_id,"
                           + "category"
                           + ") VALUES ("
                           + "?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  bean.getArticle().getId(),
                                  bean.getUser().getId(),
                                  bean.getCategory());
        bean.setId(this.findId());
    }

    @Override
    public void deleteAll() {
        this.deleteAll("r_like");

    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

    @Override
    public void save(final CollectEntity bean) {
        this.insert(bean);
    }

    @Override
    public void update(final CollectEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

}
