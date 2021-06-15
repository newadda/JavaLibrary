package org.onecell.common.spring.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QSort;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AHiberQueryDSLRepository<T,ID extends Serializable> extends AHibernateRepository<T,ID> {
    public AHiberQueryDSLRepository(HibernateTransactionManager transactionManager, SessionFactory entityManagerrFactory) {
        super(transactionManager, entityManagerrFactory);
    }

    public AHiberQueryDSLRepository(SessionFactory entityManagerrFactory) {
        super(entityManagerrFactory);
    }


    public PageImpl<T> listByPage(Pageable pageable)
    {

        Class<T> domainClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        PathBuilder<?> entity = new PathBuilder(domainClass, "entity");
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(getCurrentEntityManager());
        JPAQuery<?> from = jpaQueryFactory.from(entity);

        JPQLQuery jpqlQuery = applyPagination(pageable, from);
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

    protected  JPQLQuery applyPagination(Pageable pageable,JPAQuery query)
    {
        Querydsl querydsl = getQuerydsl();
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




    protected Querydsl getQuerydsl()
    {
        Class<T> domainClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        PathBuilder<T> builder = new PathBuilderFactory().create(domainClass);
        Querydsl querydsl = new Querydsl(getCurrentEntityManager(), builder);
        return  querydsl;
    }






}
