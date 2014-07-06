package info.tongrenlu.jdbc;

import info.tongrenlu.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class UserManager extends ManagerSupport implements
        Manager<UserEntity, Integer> {

    @Override
    public List<UserEntity> findAll() {
        final List<UserEntity> result = new ArrayList<UserEntity>();
        final String sql = "Select " + "USER_ID      as userId"
                           + "    ,   NICKNAME                  as nickname"
                           + "    ,   email                  as email"
                           + "    ,   password                  as password"
                           + "    ,   ADMIN_FLG                 as adminFlg"
                           + "    ,   RED_FLG                   as redFlg"
                           + "    ,   TRANSLATE_FLG             as translateFlg"
                           + "    ,   SIGNATURE                 as signature"
                           + "        from M_USER "
                           + "         where DEL_FLG = '0'";
        final List<Map<String, Object>> resultList = this.getOracleDao()
                                                         .queryForList(sql);
        for (final Map<String, Object> map : resultList) {
            result.add(this.mapToBean(map));
        }
        return result;
    }

    private UserEntity mapToBean(final Map<String, Object> map) {
        return new UserEntity((String) map.get("userId"),
                              (String) map.get("nickname"),
                              (String) map.get("email"),
                              (String) map.get("password"),
                              (String) map.get("adminFlg"),
                              (String) map.get("redFlg"),
                              (String) map.get("translateFlg"),
                              (String) map.get("signature"));
    }

    @Override
    public void insert(final UserEntity user) {
        final String sql = "INSERT INTO " + "m_user("
                           + "EMAIL,"
                           + "PASSWORD,"
                           + "NICKNAME,"
                           + "role,"
                           + "include_RED_FLG,"
                           + "only_TRANSLATE_FLG,"
                           + "signature"
                           + ") VALUES ("
                           + "?,?,?,?,?,?,?"
                           + ")";
        this.getMysqlDao().update(sql,
                                  user.getEmail(),
                                  user.getPassword(),
                                  user.getNickname(),
                                  user.getAdminFlg(),
                                  user.getRedFlg(),
                                  user.getTranslateFlg(),
                                  user.getSignature());
        user.setId(this.findId());
    }

    @Override
    public void save(final UserEntity user) {
        this.insert(user);
    }

    @Override
    public UserEntity findOne(final Integer id) {
        final String sql = "Select " + "id      as id"
                           + "        from m_user "
                           + "     where "
                           + "             id = ?";
        this.log.info("[sql] = " + sql);
        this.log.info("[id] = " + id);
        final Map<String, Object> result = this.getMysqlDao().queryForMap(sql,
                                                                          id);
        final UserEntity bean = new UserEntity();
        bean.setId((Integer) result.get("id"));
        return bean;
    }

    @Override
    public void deleteAll() {
        this.deleteAll("m_user");
    }

    @Override
    public void deleteOne(final Integer id) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }

    @Override
    public void update(final UserEntity bean) {
        throw new RuntimeException(new MethodNotSupportedException(""));
    }
}
