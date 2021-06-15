package org.onecell.common.misc;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SerialKeyUtil {
    private static final String STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SIZE=16;

    public int random36()
    {
        double random = Math.random();
        int intRandom=(int)(random *100d);
        return intRandom % 36;
    }


    public int checkSum(Collection<Integer> list)
    {
        int checkSum=3;
        for(Integer value:list)
        {

            checkSum=checkSum + ( value ^ (2 * checkSum));
        }

        return checkSum % 36;

    }


    public String keyGen()
    {
        List<Integer> list = new LinkedList<>();
        for(int i=0;i<(SIZE-1);i++)
        {
            list.add(random36());
        }
        int i = checkSum(list);
        list.add(i);
        StringBuilder stringBuilder= new StringBuilder();

        for(Integer item:list){

            stringBuilder.append(get_10_to_36(item));
        }

        return stringBuilder.toString();
    }

    public boolean keyValid(String key)
    {
        for(char c :key.toCharArray())
        {
            if(STR.indexOf(c)<0)
            {
                key=key.replace(String.valueOf(c),"");
            }

        }

        List<Integer> list = new LinkedList<>();


        for(char c :key.toCharArray())
        {
            list.add(get_36_to_10(Character.toString(c)));
        }

        if(list.size()<SIZE)
        {
            return false;
        }

        Integer checkSum = ((LinkedList<Integer>) list).removeLast();

        int checkSum1 = checkSum(list);

        if(checkSum == checkSum1)
        {
            return true;
        }

        return false;
    }


    public String get_10_to_36(int value)
    {
        String substring = STR.substring(value, value + 1);
        return substring;
    }

    public int get_36_to_10(String value)
    {
        int i = STR.indexOf(value);
        return i;
    }




}
