package org.onecell.common.misc;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class EucKrStringUtil {
    public static class Data{
        byte[] bytes;
        String str;
        int nextByteIndex;
        int byteLen;

        public Data(byte[] bytes, int nextByteIndex, int byteLen) {
            this.bytes = bytes;
            this.nextByteIndex = nextByteIndex;
            this.byteLen = byteLen;

        }

        public String getStr() throws UnsupportedEncodingException {
            return str = new String(bytes,CHARSET);
        }

        public byte[] getBytes() {
            return bytes;
        }

        public int getNextByteIndex() {
            return nextByteIndex;
        }

        public int getByteLen() {
            return byteLen;
        }
    }


    private  static final  String CHARSET = "EUC-KR";
    String str;
    byte[] bytes;


    public EucKrStringUtil(String str) throws UnsupportedEncodingException {
        this.str = str;
        bytes = str.getBytes(CHARSET);
    }

    private int isCharToByteLen(byte[] bytes,int byteIndex)
    {
         // 한글
        if((bytes[byteIndex] & 0x80) == 0x80)
            return 2;
        //영어
        return 1;
    }

    // 자를게 없다면 null 을 반환
    public Data cut(int byteIndex,int bytesLen) throws UnsupportedEncodingException {
        if(bytes.length <= byteIndex)
        {
            return null;
        }

        int tempIndex=byteIndex;
        for( ;(tempIndex-byteIndex) < bytesLen && tempIndex<bytes.length;)
        {
            tempIndex += isCharToByteLen(bytes, tempIndex);
        }

        if((tempIndex-byteIndex)>bytesLen)
        {
            tempIndex-=2;
        }

        /// 한글 한글자인데 byteLen이 1바이트면 한글자도 못보낸다. 방법이 없다.
        if(byteIndex==tempIndex)
        {
            return null;
        }

        return new Data(Arrays.copyOfRange(bytes,byteIndex,tempIndex),tempIndex,tempIndex-byteIndex);

    }



}
