package org.onecell.common.spring.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AHibernateRepository<T,ID extends Serializable> extends ARepository<HibernateTransactionManager,SessionFactory,Session> implements ICrudRepository<T,ID> {

    public AHibernateRepository(HibernateTransactionManager transactionManager, SessionFactory entityManagerrFactory) {
        super(transactionManager, entityManagerrFactory);
    }
    public AHibernateRepository(SessionFactory entityManagerrFactory) {
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

    public T findById(ID id)
    {
        Session entityManager = getCurrentEntityManager();
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T entity = entityManager.find(clazz, id);
        return entity;
    }

    public void update(T entity)
    {
        Session entityManager = getCurrentEntityManager();
        entityManager.update(entity);
    }

    public void merge(T entity) {
        Session entityManager = getCurrentEntityManager();
        entityManager.merge(entity);
    }



    public void deleteById(ID id)
    {
        Session entityManager = getCurrentEntityManager();
        T byId = findById(id);
        if(byId==null)
        {
            return ;
        }
        entityManager.delete(byId);
    }

    @Override
    public void delete(T entity) {
        Session entityManager = getCurrentEntityManager();
        if(entity!=null)
        {
            entityManager.delete(entity);
        }
    }

    public ID save(T entity)
    {
        Session entityManager =getCurrentEntityManager();
        ID save = (ID)(entityManager.save(entity));
        return save;
    }

    public void saveOrUpdate(T entity)
    {
        Session entityManager =getCurrentEntityManager();
        entityManager.saveOrUpdate(entity);
    }

    public void flush()
    {
        Session entityManager =getCurrentEntityManager();
        entityManager.flush();;
    }




}
