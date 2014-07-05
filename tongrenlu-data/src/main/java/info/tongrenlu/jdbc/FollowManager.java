package info.tongrenlu.jdbc;

import info.tongrenlu.entity.FollowEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class FollowManager extends ManagerSupport implements
        Manager<FollowEntity, Integer> {

    @Override
    public List<FollowEntity> findAll() {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    public List<FollowEntity> findByUserId(final String userId) {
        final List<FollowEntity> result = new ArrayList<FollowEntity>();
        final String sql = "Select  " + "    FOLLOW_ID      as followId"
                           + "    ,     USER_ID  as userId"
                           + "        from R_FOLLOW "
                           + "  where   "
                           + "      USER_ID = ?  "
                           + "  and DEL_FLG = '0'";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + userId);
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql,
                                                                       userId);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private FollowEntity mapToBean(final Map<String, Object> map) {
        return new FollowEntity((String) map.get("followId"),
                                (String) map.get("userId"));
    }

    @Override
    public FollowEntity findOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void insert(final FollowEntity bean) {
        final String sql = "INSERT INTO " + "r_like ("
                           + "like_id,"
                           + "user_id,"
                           + "category"
                           + ") VALUES ("
                           + "?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  bean.getFollow().getId(),
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
    public void save(final FollowEntity bean) {
        this.insert(bean);
    }

    @Override
    public void update(final FollowEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));

    }

}
