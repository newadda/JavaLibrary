package org.onecell.common.misc;


import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
    private static long get64LeastSignificantBitsForVersion1() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong | variant3BitFlag;
    }

    private static long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }

    /// UUID v1
    public static UUID generateType1UUID() {
        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();
        return new UUID(most64SigBits, least64SigBits);
    }

    /// 순차적 UUID
    /// UUID v1 을 기반으로  타임스탬프 위치를 바꾸어 증가성이 있게 바꾸었다.
    public static UUID generateSequentialUUID()
    {
        UUID uuid = generateType1UUID();
        String string = uuid.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(string.substring(14,18));
        sb.append("-");
        sb.append(string.substring(9,13));
        sb.append("-");
        sb.append(string.substring(0,8));
        sb.append("-");
        sb.append(string.substring(19));
        String sequentialUUIDString = sb.toString();


        return  UUID.fromString(sequentialUUIDString);
    }
}
