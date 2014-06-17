package info.tongrenlu.service;

import info.tongrenlu.constants.RedFlg;
import info.tongrenlu.constants.TranslateFlg;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.dao.ComicDao;
import info.tongrenlu.service.dao.FileDao;
import info.tongrenlu.service.dao.MusicDao;
import info.tongrenlu.service.dao.TimelineDao;
import info.tongrenlu.service.dao.UserDao;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private FileDao fileDao = null;
    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    private TimelineDao timelineDao = null;

    public String doGetConsoleIndex(final UserBean loginUser, final Model model) {

        final UserBean userBean = this.userDao.getUserInfo(loginUser.getUserId());
        loginUser.setMusicCount(userBean.getMusicCount());
        loginUser.setComicCount(userBean.getComicCount());
        loginUser.setGameCount(userBean.getGameCount());
        loginUser.setCollectCount(userBean.getCollectCount());
        loginUser.setFollowCount(userBean.getFollowCount());
        loginUser.setFansCount(userBean.getFansCount());

        if (StringUtils.equals(loginUser.getAdminFlg(), "1")) {
            model.addAttribute("unpublishMusicCount",
                               this.musicDao.countUnpublish());
            model.addAttribute("unpublishComicCount",
                               this.comicDao.countUnpublish());
        }
        return "console/index";
    }

    public String doPostUserSetting(final UserBean loginUser,
                                    final UserBean userBean,
                                    final MultipartFile avatar,
                                    final Model model) {
        if (this.userDao.validateChangeUserinfo(userBean,
                                                loginUser,
                                                model.asMap())) {
            loginUser.setNickname(userBean.getNickname());
            loginUser.setSignature(userBean.getSignature());

            final String redFlg = userBean.getRedFlg();
            final String translateFlg = userBean.getTranslateFlg();
            if (StringUtils.isNotBlank(redFlg)) {
                loginUser.setRedFlg(redFlg);
            } else {
                loginUser.setRedFlg(RedFlg.NOT_RED);
            }
            if (StringUtils.isNotBlank(translateFlg)) {
                loginUser.setTranslateFlg(translateFlg);
            } else {
                loginUser.setTranslateFlg(TranslateFlg.TRANSLATED);
            }
            this.userDao.updateUserInfo(loginUser);
            this.fileDao.saveAvatarFile(loginUser, avatar);
            return "redirect:/console/user/finish";
        }
        return "console/user/setting";
    }

    public String doPostPassword(final UserBean loginUser,
                                 final String oldPassword,
                                 final String password,
                                 final String passwordAgain,
                                 final Model model) {
        if (this.userDao.validateChangePassword(loginUser,
                                                oldPassword,
                                                password,
                                                passwordAgain,
                                                model)) {
            this.userDao.updateUserPassword(loginUser, password);
            return "redirect:/console/user/finish";
        }
        return "console/user/password";
    }

    public String doGetUserIndex(final UserBean loginUser,
                                 final String userId,
                                 final Model model) {
        if (loginUser != null) {
            loginUser.getRedFlg();
            loginUser.getTranslateFlg();
        }
        final UserBean userBean = this.userDao.getUserInfo(userId);
        if (userBean == null) {
            return "home/error/404";
        }

        model.addAttribute(userBean);

        if (loginUser != null) {
            model.addAttribute("hasFollowed",
                               this.userDao.hasFollowed(loginUser.getUserId(),
                                                        userBean.getUserId()));
        }
        return "home/user/index";
    }

    public Map<String, Object> doPostFollow(final UserBean loginUser,
                                            final String userId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            final boolean follow = this.userDao.hasFollowed(loginUser.getUserId(),
                                                            userId);
            if (follow) {
                this.userDao.removeFollow(loginUser.getUserId(), userId);
            } else {
                this.userDao.addFollow(loginUser.getUserId(), userId);
            }
            model.put("follow", follow);
            model.put("result", true);
        }
        return model;
    }

    public String doGetFollow(final UserBean loginUser,
                              final String userId,
                              final Integer page,
                              final Model model) {
        final UserBean userBean = this.userDao.getUserInfo(userId);
        if (userBean == null) {
            return "home/error/404";
        }
        model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page",
                           this.userDao.getFollowList(userBean, paginate));
        if (loginUser != null) {
            model.addAttribute("hasFollowed",
                               this.userDao.hasFollowed(loginUser.getUserId(),
                                                        userBean.getUserId()));
        }
        return "home/user/follow";
    }

    public String doGetFans(final UserBean loginUser,
                            final String userId,
                            final Integer page,
                            final Model model) {
        final UserBean userBean = this.userDao.getUserInfo(userId);
        if (userBean == null) {
            return "home/error/404";
        }
        model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page", this.userDao.getFansList(userBean, paginate));

        if (loginUser != null) {
            model.addAttribute("hasFollowed",
                               this.userDao.hasFollowed(loginUser.getUserId(),
                                                        userBean.getUserId()));
        }
        return "home/user/fans";
    }

    public String doGetComic(final UserBean loginUser,
                             final String userId,
                             final Integer page,
                             final Model model) {
        String redFlg = RedFlg.NOT_RED;
        String translateFlg = TranslateFlg.NOT_TRANSLATED;
        if (loginUser != null) {
            redFlg = loginUser.getRedFlg();
            translateFlg = loginUser.getTranslateFlg();
        }
        final UserBean userBean = this.userDao.getUserInfo(userId);
        if (userBean == null) {
            return "home/error/404";
        }
        model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page", this.comicDao.getUserComicList(userBean,
                                                                  redFlg,
                                                                  translateFlg,
                                                                  paginate));

        if (loginUser != null) {
            model.addAttribute("hasFollowed",
                               this.userDao.hasFollowed(loginUser.getUserId(),
                                                        userBean.getUserId()));
        }
        return "home/user/comic";
    }

    public String doGetMusic(final UserBean loginUser,
                             final String userId,
                             final Integer page,
                             final Model model) {
        final UserBean userBean = this.userDao.getUserInfo(userId);
        if (userBean == null) {
            return "home/error/404";
        }
        model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page",
                           this.musicDao.getUserMusicList(userBean, paginate));

        if (loginUser != null) {
            model.addAttribute("hasFollowed",
                               this.userDao.hasFollowed(loginUser.getUserId(),
                                                        userBean.getUserId()));
        }
        return "home/user/music";
    }

    public String doGetConsoleFollow(final UserBean userBean,
                                     final Integer page,
                                     final Model model) {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page",
                           this.userDao.getFollowList(userBean, paginate));
        return "console/user/follow";
    }

    public String doGetConsoleFans(final UserBean userBean,
                                   final Integer page,
                                   final Model model) {
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        model.addAttribute("page", this.userDao.getFansList(userBean, paginate));
        return "console/user/fans";
    }

    public Map<String, Object> doGetTimeline(final UserBean userBean,
                                             final Integer page) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        final String userId = userBean.getUserId();
        model.put("page", this.timelineDao.getMyTimeline(userId, paginate));
        return model;
    }
}
