package info.tongrenlu.service.dao;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MUserMapper;
import info.tongrenlu.persistence.RFollowMapper;
import info.tongrenlu.service.validator.UserBeanValidator;
import info.tongrenlu.support.PaginateSupport;
import info.tongrenlu.support.SequenceSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserDao extends SequenceSupport {

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private UserBeanValidator validator = null;
    @Autowired
    private MUserMapper userMapper = null;
    @Autowired
    private RFollowMapper followMapper = null;

    public boolean validateUserLogin(final UserBean user,
                                     final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateEmail(user.getEmail(), "email_error", model)) {
            isValid = false;
        }
        if (!this.validator.validatePassword(user.getPassword(), model)) {
            isValid = false;
        }
        return isValid;
    }

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

    public boolean validateUserRegister(final UserBean user,
                                        final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateNickname(user.getNickname(), model)) {
            isValid = false;
        }
        if (!this.validator.validatePassword(user.getPassword(), model)) {
            isValid = false;
        }
        if (!this.validator.validateEmail(user.getEmail(), "email_error", model)) {
            isValid = false;
        }
        if (isValid) {
            if (!this.validator.validateNicknameExist(user.getNickname(),
                                                      null,
                                                      model)) {
                isValid = false;
            }
            if (!this.validator.validateEmailExist(user.getEmail(),
                                                   "email_error",
                                                   model)) {
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateChangeUserinfo(final UserBean user,
                                          final UserBean loginUser,
                                          final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateNickname(user.getNickname(), model)) {
            isValid = false;
        }
        if (!this.validator.validateSignature(user.getSignature(), model)) {
            isValid = false;
        }
        if (isValid) {
            if (!this.validator.validateNicknameExist(user.getNickname(),
                                                      loginUser.getUserId(),
                                                      model)) {
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateForgotPassword(final String email,
                                          final Map<String, Object> model) {
        boolean isValid = true;
        if (!this.validator.validateEmail(email, "error", model)) {
            isValid = false;
        } else {
            if (!this.validator.validateEmailNotExist(email, "error", model)) {
                isValid = false;
            }
        }
        return isValid;
    }

    public void doUserRegister(final UserBean user) {
        user.setUserId(this.getNextId());
        final String password = user.getPassword();
        user.setPassword(DigestUtils.md5Hex(password));
        this.userMapper.insertUser(user);
    }

    public String updateUserLogin(final String userId,
                                  final String lastLoginIp,
                                  final String lastLoginUa) {
        //
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("lastLoginIp", lastLoginIp);
        param.put("lastLoginUa", lastLoginUa);
        this.userMapper.updateUser(param);
        return this.encodeUserToken(param);
    }

    public String encodeUserToken(final Map<String, Object> param) {
        final ObjectMapper mapper = new ObjectMapper();
        byte[] data = null;
        try {
            data = mapper.writeValueAsBytes(param);
        } catch (final JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (final JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.encodeBase64URLSafeString(data);
    }

    public UserBean decodeCookieToken(final String token) {
        final byte[] data = Base64.decodeBase64(token);
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, UserBean.class);
        } catch (final JsonParseException e) {
            // throw new RuntimeException(e);
            return null;
        } catch (final JsonMappingException e) {
            // throw new RuntimeException(e);
            return null;
        } catch (final IOException e) {
            // throw new RuntimeException(e);
            return null;
        }
    }

    public UserBean doUserLogin(final UserBean user,
                                final Map<String, Object> model) {
        final UserBean userBean = this.getUserByEmail(user.getEmail());
        if (userBean == null) {
            model.put("error", this.messageSource.getMessage("UserBean[Login]",
                                                             null,
                                                             null));
            return null;
        }
        String password = userBean.getPassword();
        password = DigestUtils.md5Hex(password);
        if (!StringUtils.equals(password, user.getPassword())) {
            model.put("error", this.messageSource.getMessage("UserBean[Login]",
                                                             null,
                                                             null));
            return null;
        }
        return userBean;
    }

    public void updateUserInfo(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userBean.getUserId());
        param.put("nickname", userBean.getNickname());
        param.put("signature", userBean.getSignature());
        param.put("redFlg", userBean.getRedFlg());
        param.put("translateFlg", userBean.getTranslateFlg());
        this.userMapper.updateUser(param);
    }

    public boolean validateChangePassword(final UserBean userBean,
                                          final String oldPassword,
                                          final String password,
                                          final String passwordAgain,
                                          final Model model) {
        boolean isValid = true;
        if (StringUtils.isBlank(password)) {
            model.addAttribute("password_error",
                               this.messageSource.getMessage("UserBean.password[Blank]",
                                                             null,
                                                             null));
            isValid = false;
        } else if (!StringUtils.equals(password, passwordAgain)) {
            final String message = this.messageSource.getMessage("UserBean.password[Equals]",
                                                                 null,
                                                                 null);
            model.addAttribute("password_error", message);
            model.addAttribute("passwordAgain_error", message);
            isValid = false;
        }
        if (StringUtils.isBlank(oldPassword)) {
            model.addAttribute("oldPassword_error",
                               this.messageSource.getMessage("UserBean.oldPassword[Blank]",
                                                             null,
                                                             null));
            isValid = false;
        } else {
            final UserBean userInDB = this.getUserById(userBean.getUserId());
            final String userPassword = userInDB.getPassword();
            final String formPassword = DigestUtils.md5Hex(oldPassword);
            if (!StringUtils.equals(userPassword, formPassword)) {
                model.addAttribute("oldPassword_error",
                                   this.messageSource.getMessage("UserBean.oldPassword[Equals]",
                                                                 null,
                                                                 null));
                isValid = false;
            }
        }
        return isValid;
    }

    public void updateUserPassword(final UserBean userBean,
                                   final String password) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userBean.getUserId());
        param.put("password", DigestUtils.md5Hex(password));
        this.userMapper.updateUser(param);

    }

    public UserBean getUserInfo(final String userId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        return this.userMapper.fetchUserInfo(param);
    }

    public boolean hasFollowed(final String userId, final String followId) {
        if (userId.equals(followId)) {
            return false;
        }
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("followId", followId);
        return this.followMapper.countFollow(param) > 0;
    }

    public void removeFollow(final String userId, final String followId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("followId", followId);
        this.followMapper.deleteFollow(param);
    }

    public void addFollow(final String userId, final String followId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("followId", followId);
        this.followMapper.insertFollow(param);
    }

    public int countFollow(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userBean.getUserId());
        return this.followMapper.countFollow(param);
    }

    public int countFans(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("followId", userBean.getUserId());
        return this.followMapper.countFollow(param);
    }

    public PaginateSupport getFollowList(final UserBean userBean,
                                         final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userBean.getUserId());

        final int itemCount = this.followMapper.countFollow(param);
        paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<UserBean> items = this.followMapper.fetchFollowList(param);
        paginate.setItems(items);
        return paginate;
    }

    public PaginateSupport getFansList(final UserBean userBean,
                                       final PaginateSupport paginate) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("followId", userBean.getUserId());

        final int itemCount = this.followMapper.countFollow(param);
        paginate.setItemCount(itemCount);
        paginate.compute();

        param.put("start", paginate.getStart());
        param.put("end", paginate.getEnd());
        final List<UserBean> items = this.followMapper.fetchFollowList(param);
        paginate.setItems(items);
        return paginate;
    }

    public Object getUserFollowList(final String userId,
                                    final int start,
                                    final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);

        param.put("start", start);
        param.put("end", end);

        return this.followMapper.fetchFollowList(param);
    }

    public Object getUserFansList(final String userId,
                                  final int start,
                                  final int end) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("followId", userId);

        param.put("start", start);
        param.put("end", end);

        return this.followMapper.fetchFollowList(param);
    }

    public UserBean getUserById(final String userId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        return this.userMapper.fetchUser(param);
    }

    public UserBean getUserByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        return this.userMapper.fetchUser(param);
    }

    public List<UserBean> getUserList(final String userId) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        return this.userMapper.fetchUserList(param);
    }
}
