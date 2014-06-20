package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.domain.UserProfileBean;
import info.tongrenlu.manager.ComicDao;
import info.tongrenlu.manager.MusicDao;
import info.tongrenlu.manager.TimelineDao;
import info.tongrenlu.manager.UserDao;
import info.tongrenlu.mapper.UserMapper;
import info.tongrenlu.support.PaginateSupport;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class ConsoleUserService {

    public static final int NICKNAME_LENGTH = 20;

    public static final int SIGNATURE_LENGTH = 200;

    @Autowired
    private UserMapper userMapper = null;
    @Autowired
    private MessageSource messageSource = null;

    public UserBean getUserById(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.userMapper.fetchBean(param);
    }

    public UserProfileBean getUserProfile(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.userMapper.fetchProfile(param);
    }

    @Transactional
    public boolean saveUserSetting(final UserBean inputUser,
                                   final Map<String, Object> model,
                                   final Locale locale) {
        if (this.validateSaveUserSetting(inputUser, model, locale)) {
            final Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", inputUser.getId());
            param.put("nickname", inputUser.getNickname());
            param.put("signature", inputUser.getSignature());
            param.put("includeRedFlg", inputUser.getIncludeRedFlg());
            param.put("onlyTranslateFlg", inputUser.getOnlyTranslateFlg());
            param.put("onlyVocalFlg", inputUser.getOnlyVocalFlg());
            this.userMapper.update(param);
            return true;
        }
        return false;
    }

    private boolean validateSaveUserSetting(final UserBean userBean,
                                            final Map<String, Object> model,
                                            final Locale locale) {
        boolean isValid = true;
        if (!this.validateNickname(userBean.getNickname(),
                                   "nicknameError",
                                   model,
                                   locale)) {
            isValid = false;
        }
        if (!this.validateSignature(userBean.getSignature(),
                                    "signatureError",
                                    model,
                                    locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateNickname(final String nickname,
                                     final String errorAttribute,
                                     final Map<String, Object> model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.nickname",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(nickname)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.empty",
                                                    new Object[] { fieldName },
                                                    locale));
            isValid = false;
        } else if (StringUtils.length(nickname) > LoginService.NICKNAME_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  LoginService.NICKNAME_LENGTH },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

    private boolean validateSignature(final String signature,
                                      final String errorAttribute,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.signature",
                                                               null,
                                                               locale);
        if (StringUtils.length(signature) > ConsoleUserService.SIGNATURE_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  ConsoleUserService.SIGNATURE_LENGTH },
                                                    locale));
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
