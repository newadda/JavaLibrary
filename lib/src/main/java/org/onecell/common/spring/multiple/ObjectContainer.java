package org.onecell.common.spring.multiple;

import java.util.concurrent.ConcurrentHashMap;

public class ObjectContainer {
    ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>(60);

    public void setKeyValue(Object key, Object value)
    {
        map.merge(key,value,(o, o2) -> o2);
    }

    public <T,R> R getValue(T key)
    {
        Object o = map.get(key);
        if(o!=null)
        {
            return (R)o;
        }
        return null;
    }


}
