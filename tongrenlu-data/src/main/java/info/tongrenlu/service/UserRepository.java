package info.tongrenlu.service;

import info.tongrenlu.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.MethodNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends RepositorySupport implements Repository<UserEntity, Integer> {

    @Override
    public List<UserEntity> findAll() {
        try {
            final String sql = "Select " + "USER_ID      as userId"
                    + "    ,   NICKNAME                  as nickname"
                    + "    ,   SIGNATURE                 as signature"
                    + "    ,   ADMIN_FLG                 as adminFlg"
                    + "    ,   RED_FLG                   as redFlg"
                    + "    ,   TRANSLATE_FLG             as translateFlg"
                    + "        from M_USER "
                    + "         where DEL_FLG = '0'";
            return this.getOracleDao().queryForList(sql, UserEntity.class);
        } catch (final Exception e) {
            return this.dummy();
        }

    }

    private List<UserEntity> dummy() {
        final List<UserEntity> dummy = new ArrayList<UserEntity>();
        dummy.add(new UserEntity(0,
                           "201212021000021",
                           "乡长",
                           "email",
                           "password",
                           "1",
                           "1",
                           "0",
                           "今天又是摸鱼的一天"));
        dummy.add(new UserEntity(0,
                           "201304031052404",
                           "早喵",
                           "email",
                           "password",
                           "1",
                           "1",
                           "0",
                           "この幻想郷では常识に囚われてはいけないのですね!!0▽0"));
        dummy.add(new UserEntity(0,
                           "201405021242370",
                           "犯贱老sb跳楼",
                           "email",
                           "password",
                           "1",
                           "1",
                           "0",
                           "喜欢交响风+电波歌"));
        return dummy;
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
        try {
            final String sql = "Select " + "id      as id"
                    + "        from M_USER "
                    + "     where "
                    + "             id = ?";
            this.log.info("[sql] = " + sql);
            this.log.info("[id] = " + id);
            final Map<String, Object> result = this.getMysqlDao()
                                                   .queryForMap(sql, id);
            final UserEntity user = new UserEntity();
            user.setId((Integer) result.get("id"));
            return user;
        } catch (final Exception e) {
            return null;
        }
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
