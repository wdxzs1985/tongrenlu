package info.tongrenlu.dummy;

import info.tongrenlu.entity.UserEntity;
import info.tongrenlu.jdbc.UserManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class UserManagerDummy extends UserManager {

    @Override
    public List<UserEntity> findAll() {
        final List<UserEntity> result = new ArrayList<UserEntity>();

        for (int i = 1; i <= 100; i++) {
            final UserEntity user = new UserEntity();
            user.setUserId(DummyHelper.genUserId(i));
            user.setNickname("Guest" + i);
            user.setEmail("guest" + i + "@tongrenlu.info");
            user.setPassword(DigestUtils.md5Hex("111111"));
            user.setAdminFlg("0");
            user.setRedFlg("0");
            user.setTranslateFlg("0");
            result.add(user);
        }

        return result;
    }
}
