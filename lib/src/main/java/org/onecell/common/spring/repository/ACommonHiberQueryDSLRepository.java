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

import java.util.List;

public class ACommonHiberQueryDSLRepository extends org.lib.db.repository.spring.ACommonHibernateRepository {
    public ACommonHiberQueryDSLRepository(HibernateTransactionManager transactionManager, SessionFactory entityManagerrFactory) {
        super(transactionManager, entityManagerrFactory);
    }

    public ACommonHiberQueryDSLRepository(SessionFactory entityManagerrFactory) {
        super(entityManagerrFactory);
    }


    public <T> PageImpl<T> listByPage(Class<T> clazz,Pageable pageable)
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
        Querydsl querydsl = getQuerydsl(clazz);
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




    protected   Querydsl getQuerydsl(Class clazz)
    {

        PathBuilder builder = new PathBuilderFactory().create(clazz);
        Querydsl querydsl = new Querydsl(getCurrentEntityManager(), builder);
        return  querydsl;
    }



}
