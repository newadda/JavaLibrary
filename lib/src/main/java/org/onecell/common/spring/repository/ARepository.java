package org.onecell.common.spring.repository;


import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class ARepository <TRANSAC extends PlatformTransactionManager,EMF extends EntityManagerFactory, EM extends EntityManager>  {
    private EMF entityManagerrFactory;
    private TRANSAC transactionManager;


    public ARepository(TRANSAC transactionManager,EMF entityManagerrFactory) {
        this.transactionManager = transactionManager;
        this.entityManagerrFactory = entityManagerrFactory;
    }

    public TRANSAC getTransactionManager()
    {
        return this.getTransactionManager();
    }

    public EMF getEntityManagerrFactory()
    {
        return this.entityManagerrFactory;
    }

    public EM getCurrentEntityManager()
    {
        return (EM)EntityManagerFactoryUtils.getTransactionalEntityManager(getEntityManagerrFactory());
    }

}
