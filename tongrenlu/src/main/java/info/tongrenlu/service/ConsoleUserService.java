package info.tongrenlu.service;

import info.tongrenlu.domain.TimelineBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.LikeManager;
import info.tongrenlu.manager.UserManager;
import info.tongrenlu.support.PaginateSupport;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsoleUserService {

    @Autowired
    private UserManager userManager = null;
    @Autowired
    private LikeManager likeManager = null;
    @Autowired
    private MessageSource messageSource = null;

    public UserBean getById(final Integer id) {
        return this.userManager.getById(id);
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

    public void searchFollow(final PaginateSupport<UserBean> paginate) {
        paginate.addParam("category", LikeManager.USER);

        final int itemCount = this.likeManager.countFollow(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<UserBean> items = this.likeManager.searchFollow(paginate.getParams());
        paginate.setItems(items);

    }

    public void searchFollower(final PaginateSupport<UserBean> paginate) {
        paginate.addParam("category", LikeManager.USER);

        final int itemCount = this.likeManager.countFollower(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<UserBean> items = this.likeManager.searchFollower(paginate.getParams());
        paginate.setItems(items);
    }

    public void searchTimeline(final PaginateSupport<TimelineBean> paginate) {
        final int itemCount = this.userManager.countFollowTimeline(paginate.getParams());
        paginate.setItemCount(itemCount);
        paginate.compute();

        final List<TimelineBean> items = this.userManager.searchFollowTimeline(paginate.getParams());
        paginate.setItems(items);

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

}
