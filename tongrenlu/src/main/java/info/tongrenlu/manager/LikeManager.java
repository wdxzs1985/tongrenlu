package info.tongrenlu.manager;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.DtoBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.LikeMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeManager {

    public static final String USER = "u";
    public static final String COMIC = "c";
    public static final String MUSIC = "m";

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
            category = USER;
        } else if (dtoBean instanceof MusicBean) {
            category = MUSIC;
        } else if (dtoBean instanceof ComicBean) {
            category = COMIC;
        } else {
            throw new IllegalArgumentException();
        }
        return category;
    }

}
