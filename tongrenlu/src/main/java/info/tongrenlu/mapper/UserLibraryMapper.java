package info.tongrenlu.mapper;

import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserLibraryBean;

import java.util.List;
import java.util.Map;

public interface UserLibraryMapper {

    public void insert(UserLibraryBean userLibraryBean);

    public int count(Map<String, Object> params);

    public UserLibraryBean fetchBean(Map<String, Object> params);

    public int countMusic(Map<String, Object> params);

    public List<MusicBean> fetchMusicList(Map<String, Object> params);

    public List<MusicBean> searchMusicList(Map<String, Object> params);

    public void update(UserLibraryBean userLibraryBean);

}
