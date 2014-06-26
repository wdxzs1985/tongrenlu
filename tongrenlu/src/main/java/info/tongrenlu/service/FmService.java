package info.tongrenlu.service;

import info.tongrenlu.domain.PlaylistBean;
import info.tongrenlu.domain.PlaylistTrackBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.ArticleDao;
import info.tongrenlu.manager.PlaylistDao;
import info.tongrenlu.manager.UserDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.CookieGenerator;

@Service
@Transactional
public class FmService {

    public static final String LOCAL_RESOURCE = "http://192.168.11.9/resource";

    public static final String REMOTE_RESOURCE = "http://www.tongrenlu.info/resource";

    @Autowired
    private CookieGenerator autoLoginCookieGenerator = null;
    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private PlaylistDao playlistDao = null;
    @Autowired
    private FileService fileDao = null;

    public Map<String, Object> doGetMusicAsJson(final UserBean loginUser,
                                                final Integer page,
                                                final Integer size,
                                                final String searchQuery) {

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        final PaginateSupport paginate = new PaginateSupport(page);
        model.put("searchQuery", searchQuery);
        // model.put("page", this.musicDao.getMusicList(searchQuery, null,
        // null,// loginUser
        // paginate));
        model.put("result", true);
        return model;
    }

    public Map<String, Object> doGetMusicInfo(final UserBean loginUser,
                                              final String articleId,
                                              final HttpServletRequest request) {

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        // final MusicBean musicBean = this.musicDao.getMusicById(articleId,
        // null);
        // if (musicBean != null) {
        // model.put("articleBean", musicBean);
        // final List<Object> playlist = new ArrayList<Object>();
        // final List<TrackBean> trackList =
        // this.fileDao.getMusicTracks(articleId);
        // for (final TrackBean trackBean : trackList) {
        // playlist.add(this.prepareTrackBeanModel(trackBean, request));
        // }
        // model.put("playlist", playlist);
        // this.articleDao.addAccess(musicBean, loginUser);
        // model.put("result", true);
        // }
        return model;
    }

    private Object prepareTrackBeanModel(final TrackBean trackBean,
                                         final HttpServletRequest request) {
        final String serverName = request.getServerName();
        final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1") || StringUtils.contains(serverName,
                                                                                                      "192.168.11.");
        final String FILE_PATH = isLocal ? FmService.LOCAL_RESOURCE
                : FmService.REMOTE_RESOURCE;
        // final Integer articleId = trackBean.getMusicBean().getId();
        final Integer fileId = trackBean.getFileBean().getId();
        // final String title = trackBean.getTrack();
        final String artist = trackBean.getArtist();
        // final String original = trackBean.getOriginalTitle();
        // final String album = trackBean.getMusicBean().getTitle();

        final Map<String, Object> model = new HashMap<String, Object>();
        // model.put("articleId", articleId);
        // model.put("fileId", fileId);
        // model.put("album", album);
        // model.put("title", title);
        if (StringUtils.isNotBlank(artist)) {
            model.put("artist", artist);
        }
        // if (StringUtils.isNotBlank(original)) {
        // model.put("original", original);
        // }
        // model.put("mp3", FILE_PATH + "/" + articleId + "/" + fileId +
        // ".mp3");
        // model.put("poster", FILE_PATH + "/" + articleId + "/cover_400.jpg");
        return model;
    }

    public Map<String, Object> doGetPlaylist(final Integer page,
                                             final Integer size) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final PaginateSupport paginate = new PaginateSupport(page);
        model.put("page", this.playlistDao.getPlaylistList(null, paginate));
        model.put("result", true);
        return model;
    }

    public Map<String, Object> doGetPlaylistInfo(final UserBean loginUser,
                                                 final String articleId,
                                                 final HttpServletRequest request) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        final PlaylistBean playlistBean = this.playlistDao.getPlaylistById(articleId);
        if (playlistBean != null) {
            model.put("articleBean", playlistBean);
            final List<Object> playlist = new ArrayList<Object>();
            final List<PlaylistTrackBean> trackList = this.playlistDao.getPlaylistTracks(articleId);
            for (final PlaylistTrackBean trackBean : trackList) {
                playlist.add(this.prepareTrackBeanModel(trackBean.getTrackBean(),
                                                        request));
            }
            model.put("playlist", playlist);
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doGetMyPlaylist(final UserBean loginUser,
                                               final Integer page,
                                               final Integer size) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            final PaginateSupport paginate = new PaginateSupport(page);
            model.put("page",
                      this.playlistDao.getPlaylistList(loginUser, paginate));
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doGetMyPlaylistName(final UserBean loginUser) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            model.put("items", this.playlistDao.getUserPlaylist(loginUser));
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doPostMyPlaylist(final UserBean loginUser,
                                                final String title) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        // if (this.userDao.validateUserOnline(loginUser, model)) {
        // final PlaylistBean playlistBean = new PlaylistBean();
        // playlistBean.setUserBean(loginUser);
        // playlistBean.setTitle(title);
        // if (this.playlistDao.validateCreatePlaylist(playlistBean, model)) {
        // this.playlistDao.createPlaylist(playlistBean);
        // model.put("result", true);
        // }
        // }
        return model;
    }

    public Map<String, Object> doPostRemoveMyPlaylist(final UserBean loginUser,
                                                      final String articleId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            if (StringUtils.isBlank(articleId)) {
                model.put("error", "播放列表不存在");
                return model;
            }
            final PlaylistBean playlistBean = this.playlistDao.getPlaylistById(articleId);
            if (playlistBean == null) {
                model.put("error", "播放列表不存在");
                return model;
            }
            if (!playlistBean.getUserBean().equals(loginUser)) {
                model.put("error", "不是我的播放列表");
                return model;
            }
            this.playlistDao.removePlaylist(articleId);
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doPostAddTrack(final UserBean loginUser,
                                              final String articleId,
                                              final String[] fileIds) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            if (StringUtils.isBlank(articleId)) {
                model.put("error", "播放列表不存在");
                return model;
            }
            if (fileIds == null) {
                model.put("error", "没有可以保存的曲目");
                return model;
            }
            final PlaylistBean playlistBean = this.playlistDao.getPlaylistById(articleId);
            if (playlistBean == null) {
                model.put("error", "播放列表不存在");
                return model;
            }
            if (!playlistBean.getUserBean().equals(loginUser)) {
                model.put("error", "不是我的播放列表");
                return model;
            }

            final int trackCount = this.playlistDao.countTrack(articleId);

            for (final String fileId : fileIds) {
            }
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doPostRemoveTrack(final UserBean loginUser,
                                                 final String articleId,
                                                 final String fileId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            if (StringUtils.isBlank(articleId)) {
                model.put("error", "播放列表不存在");
                return model;
            }
            final PlaylistBean playlistBean = this.playlistDao.getPlaylistById(articleId);
            if (playlistBean == null) {
                model.put("error", "播放列表不存在");
                return model;
            }
            if (!playlistBean.getUserBean().equals(loginUser)) {
                model.put("error", "不是我的播放列表");
                return model;
            }
            if (StringUtils.isBlank(fileId)) {
                model.put("error", "文件不存在");
                return model;
            }
            // final FileBean fileBean = this.fileDao.getFileInfo(fileId);
            // if (fileBean == null) {
            // model.put("error", "文件不存在");
            // return model;
            // }
            // this.playlistDao.removeTrack(playlistBean, fileBean);
            model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doGetMyCollect(final UserBean loginUser,
                                              final Integer page,
                                              final Integer size) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            // final PaginateSupport paginate = new PaginateSupport(page);
            // model.put("page",
            // this.musicDao.getMusicCollectList(loginUser, paginate));
            // model.put("result", true);
        }
        return model;
    }

    public Map<String, Object> doGetTrackAsJson(final UserBean loginUser,
                                                final Integer page,
                                                final Integer size,
                                                final String searchQuery,
                                                final HttpServletRequest request) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        final PaginateSupport paginate = new PaginateSupport(page);
        model.put("searchQuery", searchQuery);
        // model.put("page", this.fileDao.searchMusicTracks(searchQuery,
        // paginate));

        final List<Object> playlist = new ArrayList<Object>();
        final List<?> trackList = paginate.getItems();
        for (final Object next : trackList) {
            final TrackBean trackBean = (TrackBean) next;
            playlist.add(this.prepareTrackBeanModel(trackBean, request));
        }
        model.put("playlist", playlist);
        model.put("result", true);
        return model;
    }
}
