package org.onecell.common.misc;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Chars;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class ByteConverter {

    /// bytesToBigDecimal  // 바이트배열, 바이트순서, float32, float64
    /// bytesToBigInteger  // 바이트배열, 바이트순서, 부호여부
    /// byteToNumber
    /// numberToBigDecimal
    /// bigDecimalToBytes




    /**
     * 바이트 배열을 순서에 맞게 조정한다.
     * 1234 일경우 "DCBA"라면 4321로 바꾼다.
     * 즉 byte_order을 Big Endian으로 바꾼다.
     *
     * @param bytes
     * @param byte_order 예) "ABCD" : Big Endian, "DCBA" : litte Endian
     * @return
     */
    public byte[] bytesOrdering(byte[] bytes,String byte_order)
    {

        int size = bytes.length;
        if(byte_order.length()>size)
        {
            throw new ArithmeticException("overflow");
        }

        // byte_order를 정렬
        Stream<Character> sorted = Chars.asList(byte_order.toCharArray()).stream().sorted();
        List<Character> sortedByte_order = sorted.collect(Collectors.toList());

        byte[] sortedBytes = new byte[byte_order.length()];
        int i=0;
        for(Character c: sortedByte_order)
        {
            int index = byte_order.indexOf(c);

            sortedBytes[i]=0;
            if(index<bytes.length)
            {
                sortedBytes[i] = bytes[index];
            }
            i++;
        }
        return sortedBytes;
    }


    /**
     * primitive type : Byte, Short, Integer, Long, Float, Double 만 해당된다.
     * 바이트 배열이 더 크면 자른다.
     * 바이트 배열이 작으면 모자른 만큼 앞에 0x00를 추가한다.
     * @param bytes
     * @param clazz :  Byte, Short, Integer, Long, Float, Double
     * @param <R>
     * @return
     */
    public <R extends Number> R byteToNumber(byte[] bytes, Class<R> clazz)
    {
        List<Byte> byteList = Bytes.asList(bytes);

        /// 사이즈 비교후 byte 배열 크기 맞추기
        int classSize = sizeOf(clazz);
        if(byteList.size()>classSize)
        {
            for(int i=0;i<(byteList.size()-classSize);i++)
            {
                byteList.remove(0);
            }
        }else
        {
            for(int i=0;i<(byteList.size()-classSize);i++)
            {
                byteList.add(Byte.valueOf((byte)0x00));
            }
        }

        byte[] fixedBytes = Bytes.toArray(byteList);

        ByteBuffer wrap = ByteBuffer.wrap(fixedBytes);
        wrap.order(ByteOrder.BIG_ENDIAN);

        if (clazz.isAssignableFrom(Byte.class)) {
            return (R) Byte.valueOf(wrap.get());
        } else if (clazz.isAssignableFrom(Short.class)) {
            return (R)  Short.valueOf(wrap.getShort());
        } else if (clazz.isAssignableFrom(Integer.class)) {
            return (R)  Integer.valueOf(wrap.getInt());
        } else if (clazz.isAssignableFrom(Long.class)) {
            return (R) Long.valueOf(wrap.getLong());
        } else if (clazz.isAssignableFrom(Float.class)) {
            return (R) Float.valueOf((wrap.getFloat()));
        } else if (clazz.isAssignableFrom(Double.class)) {
            return (R) Double.valueOf((wrap.getDouble()));
        }

        return null;
    }








    ////////////////////////////////////////////////////////////////////////////////


    /**
     *  실수로 변환 float, double 만 해당한다.
     * @param bytes 바이트 배열
     * @param byte_order 순서(예. ABCD, DCBA)
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends Number> BigDecimal byteToBigDecimal(List<Byte> bytes, String byte_order, Class<T> clazz)
    {
        int size = sizeOf(clazz);
        if(byte_order.length()>size)
        {
            throw new ArithmeticException("overflow");
        }

        Stream<Character> sorted = Chars.asList(byte_order.toCharArray()).stream().sorted();
        List<Character> sortedByte_order = sorted.collect(Collectors.toList());

        ///// Bigendian 으로 바꾸는중
        byte[] temp = new byte[byte_order.length()];
        int i=0;
        for(Character c: sortedByte_order)
        {
            int index = byte_order.indexOf(c);

            temp[i]=0;
            if(index<bytes.size())
            {
                temp[i] = bytes.get(index);
            }
            i++;
        }

        ByteBuffer wrap = ByteBuffer.wrap(temp);
        wrap.order(ByteOrder.BIG_ENDIAN);
        if(clazz.isAssignableFrom(Float.class))
        {
            return BigDecimal.valueOf(wrap.getFloat()) ;
        }else if(clazz.isAssignableFrom(Double.class))
        {
            return BigDecimal.valueOf(wrap.getDouble()) ;
        }
        return null;
    }

    /**
     * 바이트배열을 BigInteger 로 만든다.
     * @param bytes
     * @param byte_order 순서 .예) DCBA
     * @param is_signed 부호가 존재하는 것인가?
     * @return
     */
    public  BigInteger byteToBigInteger(List<Byte> bytes, String byte_order,Boolean is_signed)
    {
        Stream<Character> sorted = Chars.asList(byte_order.toCharArray()).stream().sorted();
        List<Character> sortedByte_order = sorted.collect(Collectors.toList());

        ///// Bigendian 으로 바꾸는중
        byte[] temp = new byte[byte_order.length()];
        int i=0;
        for(Character c: sortedByte_order)
        {
            int index = byte_order.indexOf(c);

            temp[i]=0;
            if(index<bytes.size())
            {
                temp[i] = bytes.get(index);
            }
            i++;
        }

        ByteBuffer wrap = ByteBuffer.wrap(temp);
        wrap.order(ByteOrder.BIG_ENDIAN);

        if(is_signed==true)
        {
            return new BigInteger(temp);

        }else
        {
            return new BigInteger(1,temp);
        }

    }

    /**
     * float 와 double을 byte 배열로 만든다.
     * @param value
     * @param byte_order
     * @return
     */
    private byte[] floatDoubleToBytes(Object value,String byte_order)
    {
        int size = sizeOf(value.getClass());
        if(byte_order.length()!=size)
        {
            throw new ArithmeticException("size wrong");
        }
        ByteBuffer wrap = ByteBuffer.allocate(size);
        wrap.order(ByteOrder.BIG_ENDIAN);

        if(value instanceof Float)
        {
            wrap.putFloat((Float)value);
        }else if(value instanceof Double)
        {
            wrap.putDouble((Double)value);
        }else
        {
            return null;
        }

        wrap.position(0);
        byte[] bytes = new byte[size];
        wrap.get(bytes);

        Stream<Character> sorted = Chars.asList(byte_order.toCharArray()).stream().sorted();
        List<Character> sortedByte_order = sorted.collect(Collectors.toList());

        byte[] temp = new byte[byte_order.length()];


        int i=0;
        for(Character c: sortedByte_order)
        {
            int index = byte_order.indexOf(c);

            temp[index]=bytes[i];
            i++;
        }

        return temp;
    }


    public byte[] floatToBytes(Float value,String byte_order)
    {
        return floatDoubleToBytes(value,byte_order);
    }

    public byte[] doubleToBytes(Double value,String byte_order)
    {
        return floatDoubleToBytes(value,byte_order);
    }




    /**
     * BigIneger 를 Byte 배열로 바꾼다.
     *
     * byte_order 개수만큼으로 크기를 맞춘다.
     * 만약 byte_order 가 더 길다면 앞에 0x00 or OxFF 으로 byte 배열에 삽입하고
     * 만약 byte_order 가 짧다면 낮은 바이트주소에서 부터 갯수만큼 자른다.
     * @param value
     * @param byte_order byte 순서이다. 예) "ABCD" 이런식으로 순서를 정한다.
     * @return
     */
    public byte[] bigIntegerToBytes(BigInteger value, String byte_order)
    {
        byte[] bytes = value.toByteArray();

        ///// byte_order 에 value의 byte 배열의 크기를 맞춘다.
        if(bytes.length<byte_order.length()) {
            List<Byte> collect = Bytes.asList(bytes).stream().collect(Collectors.toList());
            for (int i = 0; i < (byte_order.length() - bytes.length); i++) {
                if(value.signum()>=0) {
                    collect.add(0, Byte.valueOf((byte) 0));
                }else{
                    collect.add(0, Byte.valueOf((byte) 0xFF));
                }

            }
            bytes = Bytes.toArray(collect);
        }else
        {
            List<Byte> collect = Bytes.asList(bytes).stream().collect(Collectors.toList());
            for (int i = 0; i < (bytes.length -byte_order.length()); i++) {
                collect.remove(0);
            }
            bytes = Bytes.toArray(collect);
        }


        Stream<Character> sorted = Chars.asList(byte_order.toCharArray()).stream().sorted();
        List<Character> sortedByte_order = sorted.collect(Collectors.toList());

        byte[] temp = new byte[byte_order.length()];


        int i=0;
        for(Character c: sortedByte_order)
        {
            int index = byte_order.indexOf(c);

            temp[index]=bytes[i];
            i++;
        }

        return temp;
    }







    @Deprecated
    public <R extends Number> R byteTo(List<Byte> bytes, String byte_order, Class<R> clazz) {

        int size = sizeOf(clazz);

        if(byte_order.length()>size)
        {
            throw new ArithmeticException("overflow");
        }

        List<Character> origin = byte_order.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        List<Character> order = origin.stream().sorted().collect(Collectors.toList());

        List<Byte> orderd_bytes = new LinkedList<>();
        for (Character c : order) {
            int index = origin.indexOf(c);
            try {
                Byte aByte = bytes.get(index);
                orderd_bytes.add(aByte);
            }catch (IndexOutOfBoundsException e)
            {
                orderd_bytes.add(new Byte((byte) 0x00));
            }
        }

        byte[] temp = new byte[size];
        int start = 0;
        if(size>orderd_bytes.size())
        {
            for (; start < (size-orderd_bytes.size()); start++) {
                temp[start] = 0;
            }
        }

        for (int i=0; i < orderd_bytes.size(); i++) {
            temp[start+i] = orderd_bytes.get(i);
        }

        ByteBuffer wrap = ByteBuffer.wrap(temp);
        wrap.order(ByteOrder.BIG_ENDIAN);



        if (clazz.isAssignableFrom(Byte.class)) {
            return (R) new Byte(wrap.get(0));
        } else if (clazz.isAssignableFrom(Short.class)) {
            return (R) new Short(wrap.getShort());
        } else if (clazz.isAssignableFrom(Integer.class)) {
            return (R) new Integer(wrap.getInt());
        } else if (clazz.isAssignableFrom(Long.class)) {
            return (R) new Long(wrap.getLong());
        } else if (clazz.isAssignableFrom(Float.class)) {
            return (R) new Float(wrap.getFloat());
        } else if (clazz.isAssignableFrom(Double.class)) {
            return (R) new Double(wrap.getDouble());
        }

        return null;
    }


    private int sizeOf(Class tClass) {
        if (tClass.isAssignableFrom(Byte.class)) {
            return Byte.BYTES;
        } else if (tClass.isAssignableFrom(Short.class)) {
            return Short.BYTES;
        } else if (tClass.isAssignableFrom(Integer.class)) {
            return Integer.BYTES;
        } else if (tClass.isAssignableFrom(Long.class)) {
            return Long.BYTES;
        } else if (tClass.isAssignableFrom(Float.class)) {
            return Float.BYTES;
        } else if (tClass.isAssignableFrom(Double.class)) {
            return Double.BYTES;
        }

        throw new IllegalArgumentException("없는 형식입니다.");
    }

    public BigDecimal numberToBigDecimal(Number number)
    {
        BigDecimal ret = new BigDecimal(number.toString());
        return ret;
    }

}
