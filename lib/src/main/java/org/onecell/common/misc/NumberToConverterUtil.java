package org.onecell.common.misc;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class NumberToConverterUtil {
    public static BigInteger numberToBigInteger(Number number)
    {
        if(number == null )
        {
            return null;
        }

        if(number instanceof BigDecimal)
        
        {
            BigDecimal value= (BigDecimal)number;
            return value.toBigIntegerExact();
        }else
        {
            return BigInteger.valueOf(number.longValue());
        }
    }
}
