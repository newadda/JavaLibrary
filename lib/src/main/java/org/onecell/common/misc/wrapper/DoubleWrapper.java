package org.onecell.common.misc.wrapper;

public class DoubleWrapper implements Wrapper<Double> {
    @Override
    public Double wrap(Object o) {
        if(o==null)
        {
            return null;
        }

       if(o instanceof String)
       {
           return Double.parseDouble((String)o);
       }else if(o instanceof Number)
       {
           Number value=(Number)o;
           return value.doubleValue();
       }

       return (Double)o;
    }
}
