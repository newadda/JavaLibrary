package org.onecell.common.misc.wrapper;

public class StringWrapper implements Wrapper<String> {

    @Override
    public String wrap(Object o) {
        if(o==null)
        {
            return null;
        }
       return o.toString();
    }
}
