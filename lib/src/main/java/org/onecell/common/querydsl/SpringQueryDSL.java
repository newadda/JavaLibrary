package org.onecell.common.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QSort;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class SpringQueryDSL<T> {

    EntityManager entityManager;
    Querydsl querydsl;

    public SpringQueryDSL(EntityManager entityManager) {
        this.entityManager = entityManager;
        Class<T> domainClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        PathBuilder<T> builder = new PathBuilderFactory().create(domainClass);
        Querydsl querydsl = new Querydsl(this.entityManager, builder);
    }

    public <T> PageImpl<T> listByPage(Class<T> clazz, Pageable pageable)
    {
        PathBuilder<?> entity = new PathBuilder(clazz, "entity");
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(getCurrentEntityManager());
        JPAQuery<?> from = jpaQueryFactory.from(entity);

        JPQLQuery jpqlQuery = applyPagination(clazz,pageable, from);
        QueryResults queryResults = jpqlQuery.fetchResults();
        long total = queryResults.getTotal();
        List results = queryResults.getResults();

        return new PageImpl<>(results, pageable, total);
    }




    protected  void applyOrderBy(JPAQuery query, QSort sort)
    {

        for( OrderSpecifier<?> orderSpecifier :sort.getOrderSpecifiers() )
        {
            QueryBase queryBase = query.orderBy(orderSpecifier);

        }
    }

    protected  JPQLQuery applyPagination(Class clazz,Pageable pageable,JPAQuery query)
    {
        JPQLQuery jpqlQuery = querydsl.applyPagination(pageable, query);
        return  jpqlQuery;
    }

    protected PageImpl createPage(List content,Pageable pageable,  long total)
    {
        return new PageImpl<>(content, pageable, total);
    }


    protected JPAQueryFactory getJPAQueryFactory()
    {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(getCurrentEntityManager());
        return  jpaQueryFactory;
    }

    public EntityManager getCurrentEntityManager() {
        /* 일반적인 Hibernate의 sessionFactory.GetCurrentSession() 은 동일 쓰레드에 동일한 세션을 반환하지만
        LocalSessionFactoryBean 을 통해 생성된 SessionFactory의 sessionFactory.GetCurrentSession() 은
         (EM)EntityManagerFactoryUtils.getTransactionalEntityManager(getEntityManagerrFactory()); 와 동일한 효과를 가진다.
         때문에 LocalSessionFactoryBean 을 통해 SessionFactory를 만들었다면 essionFactory.GetCurrentSession()을 사용해도 된다.
        */
        return this.entityManager;
    }



}
