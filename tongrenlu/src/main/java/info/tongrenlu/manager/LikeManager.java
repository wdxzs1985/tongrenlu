package info.tongrenlu.manager;

import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TagBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.LikeMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LikeManager {

    public static final String USER = "u";
    public static final String COMIC = "c";
    public static final String MUSIC = "m";
    public static final String TAG = "t";

    public static final int RESULT_NOT_LIKE = 0;
    public static final int RESULT_LIKE = 1;
    public static final int RESULT_NEED_SIGN = -1;
    public static final int RESULT_SELF = -2;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private LikeMapper likeMapper = null;

    public int countLike(final UserBean userBean, final DtoBean dtoBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("userBean", userBean);
        params.put("likeId", dtoBean.getId());
        params.put("category", this.getCategory(dtoBean));
        return this.likeMapper.count(params);
    }

    public void addLike(final UserBean userBean, final DtoBean dtoBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("userBean", userBean);
        params.put("likeId", dtoBean.getId());
        params.put("category", this.getCategory(dtoBean));

        this.likeMapper.insert(params);
    }

    public void removeLike(final UserBean userBean, final DtoBean dtoBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("userBean", userBean);
        params.put("likeId", dtoBean.getId());
        params.put("category", this.getCategory(dtoBean));
        this.likeMapper.delete(params);
    }

    protected String getCategory(final DtoBean dtoBean) {
        String category = null;
        if (dtoBean instanceof UserBean) {
            category = LikeManager.USER;
        } else if (dtoBean instanceof MusicBean) {
            category = LikeManager.MUSIC;
        } else if (dtoBean instanceof TagBean) {
            category = LikeManager.TAG;
        } else {
            throw new IllegalArgumentException();
        }
        return category;
    }

    public int countFollow(final Map<String, Object> params) {
        return this.likeMapper.countFollow(params);
    }

    public List<UserBean> searchFollow(final Map<String, Object> params) {
        return this.likeMapper.searchFollow(params);
    }

    public int countFollower(final Map<String, Object> params) {
        return this.likeMapper.countFollower(params);
    }

    public List<UserBean> searchFollower(final Map<String, Object> params) {
        return this.likeMapper.searchFollower(params);
    }

    public boolean validateUserNotSame(final UserBean loginUser,
                                       final UserBean userBean) {
        boolean isValid = true;
        if (loginUser.getId().equals(userBean.getId())) {
            isValid = false;
        }
        return isValid;
    }

}
