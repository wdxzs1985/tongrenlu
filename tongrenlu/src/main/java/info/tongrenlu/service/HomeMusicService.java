package info.tongrenlu.service;

import info.tongrenlu.domain.FileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.TrackBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.dao.ArticleDao;
import info.tongrenlu.service.dao.FileDao;
import info.tongrenlu.service.dao.MusicDao;
import info.tongrenlu.service.dao.TagDao;
import info.tongrenlu.service.dao.UserDao;
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
import org.springframework.ui.Model;

@Service
@Transactional
public class HomeMusicService {

    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    private ArticleDao articleDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private FileDao fileDao = null;
    @Autowired
    private TagDao tagDao = null;

    public String doGetIndex(final UserBean loginUser,
                             final Integer page,
                             final String searchQuery,
                             final Model model) {
        String userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("page", this.musicDao.getMusicList(searchQuery,
                                                              null,
                                                              userId,
                                                              paginate));
        model.addAttribute("access10Music", this.musicDao.getMusicAccess(20));
        return "home/music/index";
    }

    public String doGetView(final UserBean loginUser,
                            final String articleId,
                            final Model model) {
        String userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }
        final MusicBean musicBean = this.musicDao.getMusicById(articleId,
                                                               userId);
        if (musicBean == null) {
            return "home/error/404";
        } else if (loginUser == null) {
            if (!StringUtils.equals(musicBean.getPublishFlg(), "1")) {
                return "home/error/403";
            }
        } else if (!StringUtils.equals(loginUser.getAdminFlg(), "1")) {
            if (!StringUtils.equals(loginUser.getUserId(),
                                    musicBean.getUserBean().getUserId())) {
                if (!StringUtils.equals(musicBean.getPublishFlg(), "1")) {
                    return "home/error/403";
                }
            }
        }

        model.addAttribute("articleBean", musicBean);
        model.addAttribute(this.tagDao.getArticleTag(articleId));
        model.addAttribute("articleBeanList",
                           this.musicDao.getUserMusicList(musicBean.getUserBean(),
                                                          1,
                                                          6));
        if (loginUser != null) {
            model.addAttribute("hasFollowed",
                               this.userDao.hasFollowed(loginUser.getUserId(),
                                                        musicBean.getUserBean()
                                                                 .getUserId()));
        }
        this.articleDao.addAccess(musicBean, loginUser);
        return "home/music/view";
    }

    public List<Object> doGetPlaylist(final UserBean loginUser,
                                      final String articleId,
                                      final HttpServletRequest request) {
        final List<Object> modelList = new ArrayList<Object>();
        final List<TrackBean> trackList = this.fileDao.getMusicTracks(articleId);
        for (final TrackBean trackBean : trackList) {
            modelList.add(this.prepareTrackBeanModel(trackBean, request));
        }
        return modelList;
    }

    private Object prepareTrackBeanModel(final TrackBean trackBean,
                                         final HttpServletRequest request) {

        final String serverName = request.getServerName();
        final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1") || StringUtils.contains(serverName,
                                                                                                      "192.168.11.");
        final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
                                        : "/resource";
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", trackBean.getSongTitle());
        model.put("artist", trackBean.getLeadArtist());
        model.put("original", trackBean.getOriginalTitle());
        model.put("mp3", FILE_PATH + "/"
                         + trackBean.getFileBean().getArticleId()
                         + "/"
                         + trackBean.getFileBean().getFileId()
                         + ".mp3");
        // model.put("poster", FILE_PATH
        // + "/"
        // + trackBean.getFileBean().getArticleId()
        // + "/cover_400.jpg");

        return model;
    }

    public List<Object> doGetBooklet(final UserBean loginUser,
                                     final String articleId,
                                     final HttpServletRequest request) {
        final List<Object> modelList = new ArrayList<Object>();
        final List<FileBean> fileList = this.fileDao.getArticleFiles(articleId,
                                                                     FileDao.JPG);
        for (final FileBean fileBean : fileList) {
            modelList.add(this.prepareFileBeanModel(fileBean, request));
        }
        return modelList;
    }

    private Object prepareFileBeanModel(final FileBean fileBean,
                                        final HttpServletRequest request) {

        final String serverName = request.getServerName();
        final boolean isLocal = StringUtils.contains(serverName, "127.0.0.1") || StringUtils.contains(serverName,
                                                                                                      "192.168.11.");
        final String FILE_PATH = isLocal ? "http://192.168.11.9/resource"
                                        : "/resource";
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("normal_img", FILE_PATH + "/"
                                + fileBean.getArticleId()
                                + "/"
                                + fileBean.getFileId()
                                + "_800.jpg");
        model.put("large_img", FILE_PATH + "/"
                               + fileBean.getArticleId()
                               + "/"
                               + fileBean.getFileId()
                               + "_1600.jpg");
        return model;
    }

}
