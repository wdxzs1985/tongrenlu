package info.tongrenlu.mapper;

import info.tongrenlu.domain.ComicBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;

import java.util.List;
import java.util.Map;

public interface LikeMapper {

    int count(Map<String, Object> params);

    void insert(Map<String, Object> params);

    void delete(Map<String, Object> params);

    int countMusic(Map<String, Object> params);

    List<MusicBean> searchMusic(Map<String, Object> params);

    int countComic(Map<String, Object> params);

    List<ComicBean> searchComic(Map<String, Object> params);

    int countUser(Map<String, Object> params);

    List<UserBean> searchUser(Map<String, Object> params);

}
