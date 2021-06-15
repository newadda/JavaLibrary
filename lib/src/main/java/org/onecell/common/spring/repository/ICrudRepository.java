package org.onecell.common.spring.repository;

import java.io.Serializable;

public interface ICrudRepository <T,ID extends Serializable>{

    public T findById(ID id);


    public void update(T entity);


    public void deleteById(ID id);

    public void delete(T entity);


    public ID save(T entity);

}
