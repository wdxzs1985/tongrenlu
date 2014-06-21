package info.tongrenlu.manager;

import info.tongrenlu.domain.UserBean;
import info.tongrenlu.mapper.UserMapper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UserManager {

    public static final int EMAIL_LENGTH = 200;
    public static final int NICKNAME_LENGTH = 20;
    public static final int SIGNATURE_LENGTH = 200;

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private UserMapper userMapper = null;

    public UserBean getByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        return this.userMapper.fetchBean(param);
    }

    public UserBean getByFingerprint(final String fingerprint) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("fingerprint", fingerprint);
        return this.userMapper.fetchBean(param);
    }

    public UserBean getById(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.userMapper.fetchBean(param);
    }

    public void insert(final UserBean userBean) {
        this.userMapper.insert(userBean);
    }

    public void updateFingerprint(final UserBean userBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userBean.getId());
        params.put("fingerprint", userBean.getFingerprint());
        this.userMapper.update(params);
    }

    public void updatePassword(final UserBean userBean) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userBean.getId());
        params.put("password", userBean.getPassword());
        this.userMapper.update(params);
    }

    public void updateSetting(final UserBean userBean) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", userBean.getId());
        param.put("nickname", userBean.getNickname());
        param.put("signature", userBean.getSignature());
        param.put("includeRedFlg", userBean.getIncludeRedFlg());
        param.put("onlyTranslateFlg", userBean.getOnlyTranslateFlg());
        param.put("onlyVocalFlg", userBean.getOnlyVocalFlg());
        this.userMapper.update(param);
    }

    public boolean validateEmail(final String email,
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
                                                    locale));
            isValid = false;
        } else if (StringUtils.length(email) > UserManager.EMAIL_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  UserManager.EMAIL_LENGTH },
                                                    locale));
            isValid = false;
        } else if (!UserManager.EMAIL_PATTERN.matcher(email).matches()) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.bad",
                                                    new Object[] { fieldName },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateEmailExist(final String email,
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

    public boolean validatePassword(final String password,
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
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

    public boolean validatePassword2(final String password,
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
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateNickname(final String nickname,
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
        } else if (StringUtils.length(nickname) > UserManager.NICKNAME_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  UserManager.NICKNAME_LENGTH },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

    public boolean validateSignature(final String signature,
                                     final String errorAttribute,
                                     final Map<String, Object> model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.signature",
                                                               null,
                                                               locale);
        if (StringUtils.length(signature) > UserManager.SIGNATURE_LENGTH) {
            model.put(errorAttribute,
                      this.messageSource.getMessage("validate.tooLong",
                                                    new Object[] { fieldName,
                                                                  UserManager.SIGNATURE_LENGTH },
                                                    locale));
            isValid = false;
        }
        return isValid;
    }

}
