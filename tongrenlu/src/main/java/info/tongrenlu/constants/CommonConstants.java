package info.tongrenlu.constants;

public class CommonConstants {

    public static final long SECOND = 1000;
    public static final long MINUTE = CommonConstants.SECOND * 60;
    public static final long HOUR = CommonConstants.MINUTE * 60;
    public static final long DAY = CommonConstants.HOUR * 24;
    public static final long WEEK = CommonConstants.DAY * 7;
    public static final long MONTH = CommonConstants.DAY * 30;

    public static final String FINGERPRINT = "fingerprint";

    public static final String FORGOT_USER = "FORGOT_USER";
    public static final String LOGIN_USER = "LOGIN_USER";

    public static final String CHR_FALSE = "0";
    public static final String CHR_TRUE = "1";

    public static final String UNPUBLISH = CHR_FALSE;
    public static final String PUBLISH = CHR_TRUE;
    public static final String FREE = "2";

    public static boolean is(final String value) {
        return CommonConstants.CHR_TRUE.equals(value);
    }
}
