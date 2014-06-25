package info.tongrenlu.manager;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.PlaylistBean;
import info.tongrenlu.domain.PlaylistTrackBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.support.PaginateSupport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PlaylistDao {

    // @Autowired
    // private MArticleMapper articleMapper = null;
    // @Autowired
    // private RPlaylistMapper playlistMapper = null;

    public PaginateSupport getPlaylistList(final UserBean userBean,
                                           final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);

        // final int itemCount = this.playlistMapper.getPlaylistCount(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();

        // param.put("order", "A.ARTICLE_ID DESC");
        param.put("start", paginate.getStart());
        // param.put("end", paginate.getEnd());
        // final List<PlaylistBean> items =
        // this.playlistMapper.getPlaylistList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public List<PlaylistBean> getUserPlaylist(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userBean", userBean);
        // final List<PlaylistBean> items =
        // this.playlistMapper.getUserPlaylistName(param);
        // return items;

        return Collections.emptyList();
    }

    // public boolean validateCreatePlaylist(final PlaylistBean playlistBean,
    // final Map<String, Object> model) {
    // // boolean isValid = true;
    // // if (!this.validator.validateTitle(playlistBean.getTitle(), model)) {
    // // isValid = false;
    // // }
    // // return isValid;
    // }

    public void createPlaylist(final PlaylistBean playlistBean) {
        // this.articleMapper.insertArticle(playlistBean);
        // this.playlistMapper.insertPlaylist(playlistBean);
    }

    public void removePlaylist(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);

        // this.articleMapper.deleteArticle(param);
        // this.playlistMapper.deletePlaylist(param);
    }

    public PlaylistBean getPlaylistById(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        // return this.playlistMapper.getPlaylist(param);
        return null;
    }

    public List<PlaylistTrackBean> getPlaylistTracks(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        // return this.playlistMapper.getPlaylistTracks(param);
        return Collections.emptyList();
    }

    public int countTrack(final String articleId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("articleId", articleId);
        // return this.playlistMapper.countPlaylistTracks(param);
        return 0;
    }

    public void addTrack(final PlaylistBean playlistBean,
                         final FileBean fileBean,
                         final int orderNo) {
        final TrackBean trackBean = new TrackBean();
        trackBean.setFileBean(fileBean);

        final PlaylistTrackBean playlistTrackBean = new PlaylistTrackBean();
        playlistTrackBean.setPlaylistBean(playlistBean);
        playlistTrackBean.setTrackBean(trackBean);
        playlistTrackBean.setOrderNo(orderNo);
        // this.playlistMapper.insertPlaylistTrack(playlistTrackBean);
    }

    public void removeTrack(final PlaylistBean playlistBean,
                            final FileBean fileBean) {
        final TrackBean trackBean = new TrackBean();
        trackBean.setFileBean(fileBean);

        final PlaylistTrackBean playlistTrackBean = new PlaylistTrackBean();
        playlistTrackBean.setPlaylistBean(playlistBean);
        playlistTrackBean.setTrackBean(trackBean);

        // this.playlistMapper.deletePlaylistTrack(playlistTrackBean);
    }

}
