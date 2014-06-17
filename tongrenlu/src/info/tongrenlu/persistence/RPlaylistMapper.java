package info.tongrenlu.persistence;

import info.tongrenlu.domain.PlaylistBean;
import info.tongrenlu.domain.PlaylistTrackBean;

import java.util.List;
import java.util.Map;

public interface RPlaylistMapper {

    public int getPlaylistCount(Map<String, Object> param);

    public List<PlaylistBean> getPlaylistList(Map<String, Object> param);

    public List<PlaylistBean> getUserPlaylistName(Map<String, Object> param);

    public void insertPlaylist(PlaylistBean playlistBean);

    public void deletePlaylist(Map<String, Object> param);

    public PlaylistBean getPlaylist(Map<String, Object> param);

    public List<PlaylistTrackBean> getPlaylistTracks(Map<String, Object> param);

    public void insertPlaylistTrack(PlaylistTrackBean playlistTrackBean);

    public void deletePlaylistTrack(PlaylistTrackBean playlistTrackBean);

    public int countPlaylistTracks(Map<String, Object> param);

}
