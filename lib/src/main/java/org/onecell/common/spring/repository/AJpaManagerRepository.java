package org.onecell.common.spring.repository;

import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;

public abstract class AJpaManagerRepository<T,ID extends Serializable> extends ARepository<JpaTransactionManager, EntityManagerFactory, EntityManager> implements ICrudRepository<T,ID> {

    public AJpaManagerRepository(JpaTransactionManager transactionManager, EntityManagerFactory entityManagerrFactory) {
        super(transactionManager, entityManagerrFactory);
    }
    public AJpaManagerRepository(EntityManagerFactory entityManagerrFactory) {
        super(null, entityManagerrFactory);
    }

}
