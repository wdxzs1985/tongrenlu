package info.tongrenlu.constants;

public interface CommonConstants {

    public static final long SECOND = 1000;
    public static final long MINUTE = CommonConstants.SECOND * 60;
    public static final long HOUR = CommonConstants.MINUTE * 60;
    public static final long DAY = CommonConstants.HOUR * 24;
    public static final long WEEK = CommonConstants.DAY * 7;
    public static final long MONTH = CommonConstants.DAY * 30;

    public static final String AUTO_LOGIN = "auto_login";
    public static final String LOGIN_USER = "login_user";
}
