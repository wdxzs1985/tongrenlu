package info.tongrenlu.service.validator;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.persistence.MUserMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UserBeanValidator {

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private MUserMapper userMapper = null;

    private Pattern emailPattern = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");

    public boolean validateNickname(final String nickname,
                                    final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.isBlank(nickname)) {
            model.put("nickname_error",
                      this.messageSource.getMessage("UserBean.nickname[Blank]",
                                                    null,
                                                    null));
            isValid = false;
        } else if (StringUtils.length(nickname) > 100) {
            model.put("nickname_error",
                      this.messageSource.getMessage("UserBean.nickname[TooLong]",
                                                    new Integer[] { 20 },
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateNicknameExist(final String nickname,
                                         final String userId,
                                         final Map<String, Object> model) {
        boolean isValid = true;

        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("nickname", nickname);
        final UserBean userInDB = this.userMapper.fetchUser(param);
        if (userInDB != null) {
            if (!StringUtils.equals(userId, userInDB.getUserId())) {
                model.put("nickname_error",
                          this.messageSource.getMessage("UserBean.nickname[Exist]",
                                                        null,
                                                        null));
                isValid = false;

            }
        }
        return isValid;
    }

    public boolean validatePassword(final String password,
                                    final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.isBlank(password)) {
            model.put("password_error",
                      this.messageSource.getMessage("UserBean.password[Blank]",
                                                    null,
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateEmail(final String email,
                                 final String errorAttribute,
                                 final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.isBlank(email)) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("UserBean.email[Blank]",
                                                    null,
                                                    null));
            isValid = false;
        } else if (StringUtils.length(email) > 100) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("UserBean.email[TooLong]",
                                                    new Integer[] { 100 },
                                                    null));
            isValid = false;
        } else if (!this.emailPattern.matcher(email).matches()) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("UserBean.email[Regex]",
                                                    null,
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateEmailExist(final String email,
                                      final String errorAttribute,
                                      final Map<String, Object> model) {
        boolean isValid = true;
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        final int count = this.userMapper.countUser(param);
        if (count > 0) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("UserBean.email[Exist]",
                                                    null,
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateEmailNotExist(final String email,
                                         final String errorAttribute,
                                         final Map<String, Object> model) {
        boolean isValid = true;
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        final int count = this.userMapper.countUser(param);
        if (count == 0) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("UserBean[NotExist]",
                                                    null,
                                                    null));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateSignature(final String signature,
                                     final Map<String, Object> model) {
        boolean isValid = true;
        if (StringUtils.length(signature) > 200) {
            model.put("signature_error",
                      this.messageSource.getMessage("UserBean.signature[TooLong]",
                                                    new Integer[] { 200 },
                                                    null));
            isValid = false;
        }
        return isValid;
    }

}
