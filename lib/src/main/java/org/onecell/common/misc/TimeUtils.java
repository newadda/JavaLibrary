package org.onecell.common.misc;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class TimeUtils {
    public static LocalDateTime epochToLocalDateTime(long epochSecond,int nanoOfSecond)
    {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);
        return  LocalDateTime.ofEpochSecond(epochSecond,0, zoneOffset);
    }

    public static long localDateTimeToEpoch(LocalDateTime localDatetime)
    {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);

       return localDatetime.toEpochSecond(zoneOffset);
    }

    public static LocalDate StringToLocalDate(String date,String pattern)
    {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendPattern(pattern);
        builder.optionalStart();
        builder.parseDefaulting(ChronoField.MONTH_OF_YEAR,1);
        builder.parseDefaulting(ChronoField.DAY_OF_MONTH,1);
        builder.optionalEnd();
        DateTimeFormatter formatter = builder.toFormatter();
        LocalDate parse2 = LocalDate.parse(date, formatter);
        return parse2;
    }





}
