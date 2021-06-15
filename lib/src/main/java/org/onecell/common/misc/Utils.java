package org.onecell.common.misc;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public  class  Utils {

    /**
     * Java Generic 의 인자의 Type을 반환한다.
     * @param clazz
     * @param index
     * @return
     */
    public static Class getTypeInGeneric(Class clazz,int index)
    {
        Type type = clazz.getGenericSuperclass();

        while (!(type instanceof ParameterizedType)) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        return (Class)((ParameterizedType) type).getActualTypeArguments()[index];
        /*
        return ((Class) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[index]);*/
    }

    public static String getSimpleName(Class clazz)
    {
        return clazz.getSimpleName();
    }


}
