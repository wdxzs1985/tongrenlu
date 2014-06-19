package info.tongrenlu.manager;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.support.PaginateSupport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class UserDao {

    @Autowired
    private MessageSource messageSource = null;

    // @Autowired
    // private RFollowMapper followMapper = null;

    public boolean validateUserOnline(final UserBean userBean,
                                      final Map<String, Object> model) {
        boolean isValid = true;
        if (userBean == null) {
            isValid = false;
            model.put("error",
                      this.messageSource.getMessage("UserBean[NotLogin]",
                                                    null,
                                                    null));
        }
        return isValid;
    }

    public boolean validateChangeUserinfo(final UserBean user,
                                          final UserBean loginUser,
                                          final Map<String, Object> model) {
        final boolean isValid = true;
        // if (!this.validator.validateNickname(user.getNickname(), model)) {
        // isValid = false;
        // }
        // if (!this.validator.validateSignature(user.getSignature(), model)) {
        // isValid = false;
        // }
        // if (isValid) {
        // if (!this.validator.validateNicknameExist(user.getNickname(),
        // loginUser.getUserId(),
        // model)) {
        // isValid = false;
        // }
        // }
        return isValid;
    }

    public void updateUserInfo(final UserBean userBean) {
        // final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("userId", userBean.getUserId());
        // param.put("nickname", userBean.getNickname());
        // param.put("signature", userBean.getSignature());
        // param.put("redFlg", userBean.getRedFlg());
        // param.put("translateFlg", userBean.getTranslateFlg());
        // this.userMapper.updateUser(param);
    }

    public boolean validateChangePassword(final UserBean userBean,
                                          final String oldPassword,
                                          final String password,
                                          final String passwordAgain,
                                          final Model model) {
        final boolean isValid = true;
        // if (StringUtils.isBlank(password)) {
        // model.addAttribute("password_error",
        // this.messageSource.getMessage("UserBean.password[Blank]",
        // null,
        // null));
        // isValid = false;
        // } else if (!StringUtils.equals(password, passwordAgain)) {
        // final String message =
        // this.messageSource.getMessage("UserBean.password[Equals]",
        // null,
        // null);
        // model.addAttribute("password_error", message);
        // model.addAttribute("passwordAgain_error", message);
        // isValid = false;
        // }
        // if (StringUtils.isBlank(oldPassword)) {
        // model.addAttribute("oldPassword_error",
        // this.messageSource.getMessage("UserBean.oldPassword[Blank]",
        // null,
        // null));
        // isValid = false;
        // } else {
        // final UserBean userInDB = this.getUserById(userBean.getUserId());
        // final String userPassword = userInDB.getPassword();
        // final String formPassword = DigestUtils.md5Hex(oldPassword);
        // if (!StringUtils.equals(userPassword, formPassword)) {
        // model.addAttribute("oldPassword_error",
        // this.messageSource.getMessage("UserBean.oldPassword[Equals]",
        // null,
        // null));
        // isValid = false;
        // }
        // }
        return isValid;
    }

    public boolean hasFollowed(final String userId, final String followId) {
        if (userId.equals(followId)) {
            return false;
        }
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("followId", followId);
        // return this.followMapper.countFollow(param) > 0;
        return false;
    }

    public void removeFollow(final String userId, final String followId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("followId", followId);
        // this.followMapper.deleteFollow(param);
    }

    public void addFollow(final String userId, final String followId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("followId", followId);
        // this.followMapper.insertFollow(param);
    }

    public int countFollow(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("userId", userBean.getUserId());
        // return this.followMapper.countFollow(param);
        return 0;
    }

    public int countFans(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("followId", userBean.getUserId());
        // return this.followMapper.countFollow(param);
        return 0;
    }

    public PaginateSupport getFollowList(final UserBean userBean,
                                         final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("userId", userBean.getUserId());

        // final int itemCount = this.followMapper.countFollow(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<UserBean> items =
        // this.followMapper.fetchFollowList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public PaginateSupport getFansList(final UserBean userBean,
                                       final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        // param.put("followId", userBean.getUserId());

        // final int itemCount = this.followMapper.countFollow(param);
        // paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        // final List<UserBean> items =
        // this.followMapper.fetchFollowList(param);
        // paginate.setItems(items);
        return paginate;
    }

    public List<UserBean> getUserFollowList(final String userId,
                                            final int start,
                                            final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);

        param.put("start", start);
        param.put("end", end);

        // return this.followMapper.fetchFollowList(param);
        return Collections.emptyList();
    }

    public List<UserBean> getUserFansList(final String userId,
                                          final int start,
                                          final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("followId", userId);

        param.put("start", start);
        param.put("end", end);

        // return this.followMapper.fetchFollowList(param);
        return Collections.emptyList();
    }

}
