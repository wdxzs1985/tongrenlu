package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.LikeManager;
import info.tongrenlu.manager.UserManager;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class HomeUserSerivce {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private UserManager userManager = null;
    @Autowired
    private LikeManager likeManager = null;

    public void isLike(final Integer userId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        int result = -1;
        if (this.validateUserForLike(loginUser, model, locale)) {
            final UserBean userBean = this.userManager.getById(userId);
            if (!loginUser.equals(userBean)) {
                result = this.likeManager.countLike(loginUser, userBean);
            }
        }
        model.put("result", result);
    }

    public void doLike(final Integer userId,
                       final UserBean loginUser,
                       final Map<String, Object> model,
                       final Locale locale) {
        int result = -1;
        if (this.validateUserForLike(loginUser, model, locale)) {
            final UserBean userBean = this.userManager.getById(userId);
            if (!loginUser.equals(userBean)) {
                final int count = this.likeManager.countLike(loginUser,
                                                             userBean);
                if (count != 0) {
                    this.likeManager.removeLike(loginUser, userBean);
                    result = 0;
                } else {
                    this.likeManager.addLike(loginUser, userBean);
                    result = 1;
                }
            }
        }
        model.put("result", result);
    }

    private boolean validateUserForLike(final UserBean loginUser,
                                        final Map<String, Object> model,
                                        final Locale locale) {
        boolean isValid = true;
        if (loginUser.isGuest()) {
            final String error = this.messageSource.getMessage("error.needSignin",
                                                               null,
                                                               locale);
            model.put("error", error);
            isValid = false;
        }
        return isValid;
    }
}
