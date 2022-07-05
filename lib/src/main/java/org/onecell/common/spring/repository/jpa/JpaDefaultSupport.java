package org.onecell.common.spring.repository.jpa;

import java.io.Serializable;
import java.util.List;

interface JpaDefaultSupport<T,ID extends Serializable>  {
    List<T> listAll();
    T findById(ID id);
    void update(T entity);
    void merge(T entity);
    void deleteById(ID id);
    void delete(T entity);
    ID save(T entity);
    void saveOrUpdate(T entity);
    void flush();
    void clear();
    void flushAndClear();
    void refresh(Object o);
}
