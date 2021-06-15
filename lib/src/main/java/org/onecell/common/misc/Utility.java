package org.onecell.common.misc;

import java.math.BigDecimal;

public class Utility {
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }

    public static BigDecimal numberToBigDecimal(Number number)
    {
        BigDecimal ret = new BigDecimal(number.toString());
        return ret;
    }
}
