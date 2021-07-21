package work.sihai.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateUtil {

    private static final String FULL_PATTERN = "yyyy-mm-dd HH:MM:ss";

    private static final String DATE_PATTERN = "yyyy-mm-dd";

    private static final String TIME_PATTERN = "HH:MM:ss";

    public static LocalDateTime convertFromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date convertToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static Long getMillisByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Long getCurrentMillis() {
        return getMillisByTime(LocalDateTime.now());
    }


    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }


    public static String format(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }


    public static String formatFull(LocalDateTime time) {
        return format(time,FULL_PATTERN);
    }

    public static String formatDate(LocalDateTime time) {
        return format(time,DATE_PATTERN);
    }

    public static String formatTime(LocalDateTime time) {
        return format(time,TIME_PATTERN);
    }


    public static String formatNow(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    public static String formatNowFull() {
        return format(LocalDateTime.now(),FULL_PATTERN);
    }

    public static String formatNowDate() {
        return format(LocalDateTime.now(),DATE_PATTERN);
    }

    public static String formatNowTime() {
        return format(LocalDateTime.now(),TIME_PATTERN);
    }

    public static LocalDateTime getCurrentStart() {
        return getDayStart(LocalDateTime.now());
    }

    public static LocalDateTime getCurrentEnd(LocalDateTime time) {
        return getDayEnd(LocalDateTime.now());
    }


    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999_999_999);
    }


}
