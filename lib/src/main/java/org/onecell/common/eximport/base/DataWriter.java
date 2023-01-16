package org.onecell.common.eximport.base;

import java.io.OutputStream;

public interface DataWriter <D>{
   void write(D data,OutputStream outputStream);
}
