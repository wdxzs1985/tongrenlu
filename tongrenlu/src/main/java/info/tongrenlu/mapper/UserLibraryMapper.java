package info.tongrenlu.mapper;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserLibraryBean;
import info.tongrenlu.domain.UserProfileBean;

import java.util.List;
import java.util.Map;

public interface UserLibraryMapper {

    public void insert(UserLibraryBean userLibraryBean);

    public UserLibraryBean fetchBean(Map<String, Object> params);

    public int countMusic(Map<String, Object> params);

    public List<MusicBean> fetchMusicList(Map<String, Object> params);

    public List<MusicBean> searchMusicList(Map<String, Object> params);

    public void update(UserLibraryBean userLibraryBean);

    public List<UserProfileBean> searchUserList(Map<String, Object> params);

    public int countUser(Map<String, Object> params);

}
