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
    public static final Integer INT_FALSE = 0;
    public static final Integer INT_TRUE = 1;

    public static boolean is(final String value) {
        return CommonConstants.CHR_TRUE.equals(value);
    }

    public static boolean is(final Integer value) {
        return INT_TRUE.equals(value);
    }

}
