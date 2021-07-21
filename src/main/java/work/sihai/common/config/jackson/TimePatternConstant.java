package work.sihai.common.config.jackson;

import java.time.format.DateTimeFormatter;

/**
 * @author 闫四海
 * @date 2020-12-03 18:45
 */
public class TimePatternConstant {

    public static final String YEAR_TIME_STR = "yyyy-MM-dd HH:mm:ss";

    public static final String YEAR_STR = "yyyy-MM-dd";

    public static final String TIME_STR = "HH:mm:ss";

    public static final DateTimeFormatter YEAR_TIME_PATTERN = DateTimeFormatter.ofPattern(YEAR_TIME_STR);

    public static final DateTimeFormatter YEAR_PATTERN = DateTimeFormatter.ofPattern(YEAR_STR);

    public static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern(TIME_STR);

}
