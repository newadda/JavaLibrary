package org.onecell.common.validation;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;


public final class ValidationUtils {
    /**
     *
     *
     *
     *
     * <code><pre>
     * 예제)
       LocalDateTime start_datetime = requestDto.getStart_datetime();
       LocalDateTime  end_datetime= requestDto.getEnd_datetime();
       boolean periodOver = ValidationUtils.isPeriodOver(start_datetime, end_datetime, ChronoUnit.SECONDS, TimeUnit.HOURS.toSeconds(48) );
       if(periodOver)
       {
            throw new PeriodDayOverException(2);
       }
     * </pre></code>
     *
     *
     *
     *
     * @param since
     * @param until
     * @param unit
     * @param period
     * @return
     */
    public static boolean isPeriodOver(Temporal since, Temporal until, TemporalUnit unit, long period)
    {
        ChronoUnit seconds = ChronoUnit.SECONDS;

        long until1 = since.until(until, unit);
        if(until1>period)
        {
            return true;
        }

        return false;
    }



}
