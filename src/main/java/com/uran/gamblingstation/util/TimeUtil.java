package com.uran.gamblingstation.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    public static final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    public static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(3000, 1, 1, 23, 59);

    private TimeUtil() {
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

    // new
    public static LocalDateTime parseToLocalDateTime(String localDate, String localTime){
        if (StringUtils.isEmpty(localDate) || StringUtils.isEmpty(localTime)) return null;
        LocalDate ld = parseLocalDate(localDate);
        LocalTime lt = parseLocalTime(localTime);
        return ld ==null || lt == null ? null : LocalDateTime.of(ld, lt);
    }
}
