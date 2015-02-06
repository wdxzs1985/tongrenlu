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

    public static final String UNPUBLISH = CHR_FALSE;
    public static final String PUBLISH = CHR_TRUE;
    public static final String FREE = "2";

    public static final Integer ROLE_GUEST = 0;
    // 1
    public static final Integer ROLE_MEMBER = Integer.valueOf("0000000001", 2);
    // 3
    public static final Integer ROLE_EDITOR = Integer.valueOf("0000000010", 2);
    // 7
    public static final Integer ROLE_EDIT_ADMIN = Integer.valueOf("0000000110",
                                                                  2);
    // 9
    public static final Integer ROLE_SHOP_ADMIN = Integer.valueOf("0000001000",
                                                                  2);
    // 513
    public static final Integer ROLE_SUPER_ADMIN = Integer.valueOf("1000000000",
                                                                   2);

    public static boolean is(final String value) {
        return CommonConstants.CHR_TRUE.equals(value);
    }

    public static boolean is(final Integer value) {
        return INT_TRUE.equals(value);
    }

}
