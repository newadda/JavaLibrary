package org.onecell.common.spring.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import java.io.Serializable;

public class ACommonHibernateRepository extends ARepository<HibernateTransactionManager, SessionFactory, Session>  {

    public ACommonHibernateRepository(HibernateTransactionManager transactionManager, SessionFactory entityManagerrFactory) {
        super(transactionManager, entityManagerrFactory);
    }
    public ACommonHibernateRepository(SessionFactory entityManagerrFactory) {
        super(null, entityManagerrFactory);
    }

    @Override
    public Session getCurrentEntityManager() {
        /* 일반적인 Hibernate의 sessionFactory.GetCurrentSession() 은 동일 쓰레드에 동일한 세션을 반환하지만
        LocalSessionFactoryBean 을 통해 생성된 SessionFactory의 sessionFactory.GetCurrentSession() 은
         (EM)EntityManagerFactoryUtils.getTransactionalEntityManager(getEntityManagerrFactory()); 와 동일한 효과를 가진다.
         때문에 LocalSessionFactoryBean 을 통해 SessionFactory를 만들었다면 essionFactory.GetCurrentSession()을 사용해도 된다.
        */
        return getEntityManagerrFactory().getCurrentSession();
    }


    public <T> T findById(Class<T> clazz,Serializable id) {
        return clazz.cast(getCurrentEntityManager().find(clazz,id));
    }

    public  void update(Object entity)
    {
        Session entityManager = getCurrentEntityManager();
        entityManager.update(entity);
    }

    public  void deleteById(Class clazz,Serializable id)
    {
        Session entityManager = getCurrentEntityManager();
        Object byId = findById(clazz, id);
        if(byId==null)
        {
            return ;
        }
        entityManager.delete(byId);
    }

    public  Serializable save(Object entity)
    {
        Session entityManager =getCurrentEntityManager();
        Serializable save = entityManager.save(entity);
        return save;

    }

    public<T> T merge(Object entity)
    {   Session entityManager =getCurrentEntityManager();
        Object merge = entityManager.merge(entity);
        return (T)merge;
    }
}
