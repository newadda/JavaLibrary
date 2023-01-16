package org.onecell.common.eximport.base;

import java.io.InputStream;
import java.util.List;

public interface DataReader<D> {
    List<D> read(InputStream inputStream,Class<D> clazz);
}
