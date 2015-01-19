package info.tongrenlu.service;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.manager.FileManager;
import info.tongrenlu.manager.UserManager;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {

    @Autowired
    private final UserManager userManager = null;
    @Autowired
    private final MessageSource messageSource = null;
    @Autowired
    private final FileManager fileManager = null;

    public String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(4);
    }

    @Transactional
    public UserBean doSignIn(final UserBean inputUser, final String userAgent, final Map<String, Object> model, final Locale locale) {
        if (this.validateForLoginInput(inputUser, model, locale)) {
            final UserBean loginUser = this.userManager.getByEmail(inputUser.getEmail());
            if (this.validateForLogin(inputUser, loginUser, model, locale)) {
                this.updateFingerprint(loginUser, userAgent);
                return loginUser;
            }
        }
        return null;
    }

    @Transactional
    public UserBean doAutoLogin(final String fingerprint, final String userAgent) {
        UserBean loginUser = this.userManager.getByFingerprint(fingerprint, userAgent);
        if (loginUser != null) {
            this.updateFingerprint(loginUser, userAgent);
        }
        return loginUser;
    }

    private void updateFingerprint(final UserBean loginUser, final String userAgent) {
        final String fingerprint = RandomStringUtils.randomAlphanumeric(32);
        loginUser.setFingerprint(fingerprint);
        this.userManager.updateFingerprint(loginUser, fingerprint, userAgent);
    }

    @Transactional
    public boolean doSignup(final UserBean inputUser, final Map<String, Object> model, final Locale locale) {
        if (this.validateForRegister(inputUser, model, locale)) {
            this.userManager.insert(inputUser);
            return true;
        }
        return false;
    }

    public boolean doFindForgotUser(final UserBean inputUser, final Map<String, Object> model, final Locale locale) {
        if (this.validateForFindForgotUserInput(inputUser, model, locale)) {
            final UserBean loginUser = this.userManager.getByEmail(inputUser.getEmail());
            if (this.validateForFindForgotUser(inputUser, loginUser, model, locale)) {
                inputUser.setId(loginUser.getId());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean doChangePassword(final UserBean inputUser, final Map<String, Object> model, final Locale locale) {
        if (this.validateForChangePassword(inputUser, model, locale)) {
            this.userManager.updatePassword(inputUser);
            return true;
        }
        return false;
    }

    public boolean validateForLoginInput(final UserBean userBean, final Map<String, Object> model, final Locale locale) {
        boolean isValid = true;
        if (!this.userManager.validateEmail(userBean.getEmail(), "emailError", model, locale)) {
            isValid = false;
        }
        if (!this.userManager.validatePassword(userBean.getPassword(), "passwordError", model, locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForLogin(final UserBean userBean, final UserBean loginUser, final Map<String, Object> model, final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("error.signIn", null, locale);
            model.put("error", message);
            isValid = false;
        } else {
            final String password = DigestUtils.md5Hex(loginUser.getPassword() + userBean.getSalt());
            if (!StringUtils.equals(userBean.getPassword(), password)) {
                final String message = this.messageSource.getMessage("error.signIn", null, locale);
                model.put("error", message);
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateForRegister(final UserBean inputUser, final Map<String, Object> model, final Locale locale) {
        boolean isValid = true;
        if (!this.userManager.validateEmail(inputUser.getEmail(), "emailError", model, locale)) {
            isValid = false;
        } else if (!this.userManager.validateEmailExist(inputUser.getEmail(), "emailError", model, locale)) {
            isValid = false;
        }
        if (!this.userManager.validatePassword(inputUser.getPassword(), "passwordError", model, locale)) {
            isValid = false;
        } else if (!this.userManager.validatePassword2(inputUser.getPassword(), inputUser.getPassword2(),
                                                       "password2Error", model, locale)) {
            isValid = false;
        }

        if (!this.userManager.validateNickname(inputUser.getNickname(), "nicknameError", model, locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForFindForgotUserInput(final UserBean userBean, final Map<String, Object> model, final Locale locale) {
        boolean isValid = true;
        if (!this.userManager.validateEmail(userBean.getEmail(), "emailError", model, locale)) {
            isValid = false;
        }
        if (!this.userManager.validateNickname(userBean.getNickname(), "nicknameError", model, locale)) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateForFindForgotUser(final UserBean inputUser, final UserBean loginUser, final Map<String, Object> model, final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("error.findForgotUser", null, locale);
            model.put("error", message);
            isValid = false;
        } else {
            if (!StringUtils.equals(inputUser.getNickname(), loginUser.getNickname())) {
                final String message = this.messageSource.getMessage("error.findForgotUser", null, locale);
                model.put("error", message);
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean validateForChangePassword(final UserBean inputUser, final Map<String, Object> model, final Locale locale) {
        boolean isValid = true;
        if (!this.userManager.validatePassword(inputUser.getPassword(), "passwordError", model, locale)) {
            isValid = false;
        } else if (!this.userManager.validatePassword2(inputUser.getPassword(), inputUser.getPassword2(),
                                                       "password2Error", model, locale)) {
            isValid = false;
        }
        return isValid;
    }
}
