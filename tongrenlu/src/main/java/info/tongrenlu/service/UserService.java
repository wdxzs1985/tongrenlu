package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.mapper.UserMapper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    public static final int NICKNAME_LENGTH = 20;
    public static final int EMAIL_LENGTH = 200;
    public static final int SIGNATURE_LENGTH = 200;

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private UserMapper userMapper = null;
    @Autowired
    private FileManager fileManager = null;

    @Transactional
    public UserBean doSignIn(final UserBean inputUser,
                             final Map<String, Object> model,
                             final Locale locale) {
        if (this.validateForLoginInput(inputUser, model, locale)) {
            final UserBean loginUser = this.getByEmail(inputUser.getEmail());
            if (this.validateForLogin(inputUser, loginUser, model, locale)) {
                final String fingerprint = RandomStringUtils.randomAlphanumeric(32);
                loginUser.setFingerprint(fingerprint);
                this.updateFingerprint(loginUser);
                return loginUser;
            }
        }
        return null;
    }

    @Transactional
    public UserBean doAutoLogin(final String fingerprint) {
        UserBean loginUser = null;
        if (StringUtils.isNotBlank(fingerprint)) {
            loginUser = this.getByFingerprint(fingerprint);
            if (loginUser != null) {
                final String newFingerprint = RandomStringUtils.randomAlphanumeric(32);
                loginUser.setFingerprint(newFingerprint);
                this.updateFingerprint(loginUser);
            }
        }
        return loginUser;
    }

    @Transactional
    public UserBean doSignup(final UserBean inputUser,
                             final Map<String, Object> model,
                             final Locale locale) {
        if (this.validateForRegister(inputUser, model, locale)) {
            // this.userDao.doUserRegister(userBean);
            // this.fileDao.saveAvatarFile(userBean, null);
            this.userMapper.insert(inputUser);
            this.fileManager.saveAvatarFile(inputUser, null);
            return inputUser;
        }
        return null;
    }

    public UserBean doFindForgotUser(final UserBean inputUser,
                                     final Map<String, Object> model,
                                     final Locale locale) {
        if (this.validateForFindForgotUserInput(inputUser, model, locale)) {
            final UserBean loginUser = this.getByEmail(inputUser.getEmail());
            if (this.validateForFindForgotUser(inputUser,
                                               loginUser,
                                               model,
                                               locale)) {
                return loginUser;
            }
        }
        return null;
    }

    @Transactional
    public boolean doChangePassword(final UserBean inputUser,
                                    final Map<String, Object> model,
                                    final Locale locale) {
        if (this.validateForChangePassword(inputUser, model, locale)) {
            this.updatePassword(inputUser);
            return true;
        }
        return false;
    }

    private UserBean getByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        return this.userMapper.fetchBean(param);
    }

    private UserBean getByFingerprint(final String fingerprint) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fingerprint", fingerprint);
        return this.userMapper.fetchBean(param);
    }

    private void updateFingerprint(final UserBean userBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userBean.getId());
        params.put("fingerprint", userBean.getFingerprint());
        this.userMapper.update(params);
    }

    private void updatePassword(final UserBean userBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userBean.getId());
        params.put("password", userBean.getPassword());
        this.userMapper.update(params);
    }

    public boolean validateForLoginInput(final UserBean userBean,
                                         final Map<String, Object> model,
                                         final Locale locale) {
        boolean isValid = true;
        if (!this.validateEmail(userBean.getEmail(),
                                "emailError",
                                model,
                                locale)) {
            isValid = false;
        }
        if (!this.validatePassword(userBean.getPassword(),
                                   "passwordError",
                                   model,
                                   locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForLogin(final UserBean userBean,
                                    final UserBean loginUser,
                                    final Map<String, Object> model,
                                    final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("error.signIn",
                                                                 null,
                                                                 locale);
            model.put("error", message);
            isValid = false;
        } else {
            final String password = DigestUtils.md5Hex(loginUser.getPassword() + userBean.getSalt());
            if (!StringUtils.equals(userBean.getPassword(), password)) {
                final String message = this.messageSource.getMessage("error.signIn",
                                                                     null,
                                                                     locale);
                model.put("error", message);
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateForRegister(final UserBean inputUser,
                                       final Map<String, Object> model,
                                       final Locale locale) {
        boolean isValid = true;
        if (!this.validateEmail(inputUser.getEmail(),
                                "emailError",
                                model,
                                locale)) {
            isValid = false;
        } else if (!this.validateEmailExist(inputUser.getEmail(),
                                            "emailError",
                                            model,
                                            locale)) {
            isValid = false;
        }
        if (!this.validatePassword(inputUser.getPassword(),
                                   "passwordError",
                                   model,
                                   locale)) {
            isValid = false;
        } else if (!this.validatePassword2(inputUser.getPassword(),
                                           inputUser.getPassword2(),
                                           "password2Error",
                                           model,
                                           locale)) {
            isValid = false;
        }

        if (!this.validateNickname(inputUser.getNickname(),
                                   "nicknameError",
                                   model,
                                   locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForFindForgotUserInput(final UserBean userBean,
                                                  final Map<String, Object> model,
                                                  final Locale locale) {
        boolean isValid = true;
        if (!this.validateEmail(userBean.getEmail(),
                                "emailError",
                                model,
                                locale)) {
            isValid = false;
        }
        if (!this.validateNickname(userBean.getNickname(),
                                   "nicknameError",
                                   model,
                                   locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForFindForgotUser(final UserBean inputUser,
                                             final UserBean loginUser,
                                             final Map<String, Object> model,
                                             final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("error.findForgotUser",
                                                                 null,
                                                                 locale);
            model.put("error", message);
            isValid = false;
        } else {
            if (!StringUtils.equals(inputUser.getNickname(),
                                    loginUser.getNickname())) {
                final String message = this.messageSource.getMessage("error.findForgotUser",
                                                                     null,
                                                                     locale);
                model.put("error", message);
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateForChangePassword(final UserBean inputUser,
                                             final Map<String, Object> model,
                                             final Locale locale) {
        boolean isValid = true;
        if (!this.validatePassword(inputUser.getPassword(),
                                   "passwordError",
                                   model,
                                   locale)) {
            isValid = false;
        } else if (!this.validatePassword2(inputUser.getPassword(),
                                           inputUser.getPassword2(),
                                           "password2Error",
                                           model,
                                           locale)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateEmail(final String email,
                                  final String errorAttribute,
                                  final Map<String, Object> model,
                                  final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.email",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(email)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.empty",
                                                    new Object[] { fieldName },
                                                    null));
            isValid = false;
        } else if (StringUtils.length(email) > UserService.EMAIL_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  UserService.EMAIL_LENGTH },
                                                    null));
            isValid = false;
        } else if (!UserService.EMAIL_PATTERN.matcher(email).matches()) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.bad",
                                                    new Object[] { fieldName },
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    private boolean validateEmailExist(final String email,
                                       final String errorAttribute,
                                       final Map<String, Object> model,
                                       final Locale locale) {
        boolean isValid = true;
        final UserBean userBean = this.getByEmail(email);
        if (userBean != null) {
            final String fieldName = this.messageSource.getMessage("UserBean.email",
                                                                   null,
                                                                   locale);
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.unique",
                                                    new Object[] { fieldName },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

    private boolean validatePassword(final String password,
                                     final String errorAttribute,
                                     final Map<String, Object> model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.password",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(password)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.empty",
                                                    new Object[] { fieldName },
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    private boolean validatePassword2(final String password,
                                      final String password2,
                                      final String errorAttribute,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName1 = this.messageSource.getMessage("UserBean.password",
                                                                null,
                                                                locale);
        final String fieldName2 = this.messageSource.getMessage("UserBean.password2",
                                                                null,
                                                                locale);
        if (!StringUtils.equals(password, password2)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.notSame",
                                                    new Object[] { fieldName1,
                                                                  fieldName2 },
                                                    null));
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
                                                    null));
            isValid = false;
        } else if (StringUtils.length(nickname) > UserService.NICKNAME_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  UserService.NICKNAME_LENGTH },
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateSignature(final String signature,
                                     final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.length(signature) > UserService.SIGNATURE_LENGTH) {
            model.put("signature_error",
                      this.messageSource.getMessage("UserBean.signature[TooLong]",
                                                    new Integer[] { UserService.SIGNATURE_LENGTH },
                                                    null));
            isValid = false;
        }
        return isValid;
    }
}
