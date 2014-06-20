package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.manager.ComicDao;
import info.tongrenlu.manager.MusicDao;
import info.tongrenlu.manager.TimelineDao;
import info.tongrenlu.manager.UserDao;
import info.tongrenlu.manager.UserManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class ConsoleUserService {

    @Autowired
    private UserManager userManager = null;
    @Autowired
    private MessageSource messageSource = null;

    public UserProfileBean getProfileById(final Integer id) {
        return this.userManager.getProfileById(id);
    }

    public boolean saveSetting(final UserBean inputUser,
                               final Map<String, Object> model,
                               final Locale locale) {
        if (this.validateForSaveSetting(inputUser, model, locale)) {
            this.userManager.updateSetting(inputUser);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changePassword(final UserBean inputUser,
                                  final Map<String, Object> model,
                                  final Locale locale) {
        if (this.validateForChangePassword(inputUser, model, locale)) {
            this.userManager.updatePassword(inputUser);
            return true;
        }
        return false;
    }

    public boolean validateForSaveSetting(final UserBean userBean,
                                          final Map<String, Object> model,
                                          final Locale locale) {
        boolean isValid = true;
        if (!this.userManager.validateNickname(userBean.getNickname(),
                                               "nicknameError",
                                               model,
                                               locale)) {
            isValid = false;
        }
        if (!this.userManager.validateSignature(userBean.getSignature(),
                                                "signatureError",
                                                model,
                                                locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForChangePassword(final UserBean inputUser,
                                             final Map<String, Object> model,
                                             final Locale locale) {
        boolean isValid = true;
        if (!this.userManager.validatePassword(inputUser.getPassword(),
                                               "passwordError",
                                               model,
                                               locale)) {
            isValid = false;
        } else if (!this.userManager.validatePassword2(inputUser.getPassword(),
                                                       inputUser.getPassword2(),
                                                       "password2Error",
                                                       model,
                                                       locale)) {
            isValid = false;
        }
        return isValid;
    }

    @Autowired
    private UserDao userDao = null;
    @Autowired
    private FileService fileDao = null;
    @Autowired
    private ComicDao comicDao = null;
    @Autowired
    private MusicDao musicDao = null;
    @Autowired
    private TimelineDao timelineDao = null;

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
            // this.userDao.updateUserPassword(loginUser, password);
            return "redirect:/console/user/finish";
        }
        return "console/user/password";
    }

    public String doGetUserIndex(final UserBean loginUser,
                                 final String userId,
                                 final Model model) {
        // if (loginUser != null) {
        // loginUser.getRedFlg();
        // loginUser.getTranslateFlg();
        // }
        // final UserBean userBean = this.userDao.getUserInfo(userId);
        // if (userBean == null) {
        // return "home/error/404";
        // }
        //
        // model.addAttribute(userBean);

        if (loginUser != null) {
            // model.addAttribute("hasFollowed",
            // this.userDao.hasFollowed(loginUser.getUserId(),
            // userBean.getUserId()));
        }
        return "home/user/index";
    }

    public Map<String, Object> doPostFollow(final UserBean loginUser,
                                            final String userId) {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        if (this.userDao.validateUserOnline(loginUser, model)) {
            // final boolean follow =
            // this.userDao.hasFollowed(loginUser.getUserId(),
            // userId);
            // if (follow) {
            // this.userDao.removeFollow(loginUser.getUserId(), userId);
            // } else {
            // this.userDao.addFollow(loginUser.getUserId(), userId);
            // }
            // model.put("follow", follow);
            model.put("result", true);
        }
        return model;
    }

    public String doGetFollow(final UserBean loginUser,
                              final String userId,
                              final Integer page,
                              final Model model) {
        // final UserBean userBean = this.userDao.getUserInfo(userId);
        // if (userBean == null) {
        // return "home/error/404";
        // }
        // model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        // model.addAttribute("page",
        // this.userDao.getFollowList(userBean, paginate));
        if (loginUser != null) {
            // model.addAttribute("hasFollowed",
            // this.userDao.hasFollowed(loginUser.getUserId(),
            // userBean.getUserId()));
        }
        return "home/user/follow";
    }

    public String doGetFans(final UserBean loginUser,
                            final String userId,
                            final Integer page,
                            final Model model) {
        // final UserBean userBean = this.userDao.getUserInfo(userId);
        // if (userBean == null) {
        // return "home/error/404";
        // }
        // model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        // model.addAttribute("page", this.userDao.getFansList(userBean,
        // paginate));

        if (loginUser != null) {
            // model.addAttribute("hasFollowed",
            // this.userDao.hasFollowed(loginUser.getUserId(),
            // userBean.getUserId()));
        }
        return "home/user/fans";
    }

    public String doGetComic(final UserBean loginUser,
                             final String userId,
                             final Integer page,
                             final Model model) {
        // String redFlg = RedFlg.NOT_RED;
        // String translateFlg = TranslateFlg.NOT_TRANSLATED;
        // if (loginUser != null) {
        // redFlg = loginUser.getRedFlg();
        // translateFlg = loginUser.getTranslateFlg();
        // }
        // final UserBean userBean = this.userDao.getUserInfo(userId);
        // if (userBean == null) {
        // return "home/error/404";
        // }
        // model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        // model.addAttribute("page", this.comicDao.getUserComicList(userBean,
        // redFlg,
        // translateFlg,
        // paginate));

        if (loginUser != null) {
            // model.addAttribute("hasFollowed",
            // this.userDao.hasFollowed(loginUser.getUserId(),
            // userBean.getUserId()));
        }
        return "home/user/comic";
    }

    public String doGetMusic(final UserBean loginUser,
                             final String userId,
                             final Integer page,
                             final Model model) {
        // final UserBean userBean = this.userDao.getUserInfo(userId);
        // if (userBean == null) {
        // return "home/error/404";
        // }
        // model.addAttribute(userBean);
        final PaginateSupport paginate = new PaginateSupport();
        paginate.setPage(page);
        paginate.setSize(10);
        // model.addAttribute("page",
        // this.musicDao.getUserMusicList(userBean, paginate));

        if (loginUser != null) {
            // model.addAttribute("hasFollowed",
            // this.userDao.hasFollowed(loginUser.getUserId(),
            // userBean.getUserId()));
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
        // final String userId = userBean.getUserId();
        // model.put("page", this.timelineDao.getMyTimeline(userId, paginate));
        return model;
    }
}
