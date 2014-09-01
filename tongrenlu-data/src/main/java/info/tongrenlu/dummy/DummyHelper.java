package info.tongrenlu.dummy;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

public class DummyHelper {

    static final Random SEED = new Random(System.currentTimeMillis());

    static final String PREFIX = "20140707";

    static final int RANDOM_RANGE = 100;

    static final int USER_ID_OFFSET = 1000;
    static final int MUSIC_ID_OFFSET = 2000;
    static final int COMIC_ID_OFFSET = 3000;
    static final int TAG_ID_OFFSET = 5000;

    static final int INT_COMMENT_ID = 10000;
    static final AtomicInteger COMMENT_ID = new AtomicInteger(INT_COMMENT_ID);

    public static int nextInt(final int n) {
        return SEED.nextInt(n) + 1;
    }

    public static String formatId(final int id) {
        return PREFIX + StringUtils.leftPad(String.valueOf(id), 7, "0");
    }

    // 1000 ~ 1999
    public static String genUserId(final int seq) {
        return formatId(USER_ID_OFFSET + seq);
    }

    // 2000 ~ 2999
    public static String genMusicId(final int seq) {
        return formatId(MUSIC_ID_OFFSET + seq);
    }

    // 3000 ~ 3999
    public static String genComicId(final int seq) {
        return formatId(COMIC_ID_OFFSET + seq);
    }

    public static String genTagId(final int seq) {
        return formatId(TAG_ID_OFFSET + seq);
    }

    // 200000~
    public static String genCommentId() {
        return formatId(COMMENT_ID.incrementAndGet());
    }

    public static String randomUserId() {
        return genUserId(nextInt(RANDOM_RANGE));
    }

    public static String randomTagId() {
        return genTagId(nextInt(RANDOM_RANGE));
    }
}
