package org.onecell.common.spring.util;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import org.springframework.data.jpa.repository.support.Querydsl;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;

public class QuerydslPaginationUtil {

    public static Querydsl getQuerydsl(Class<?> domainClass, EntityManager entityManager) {
        PathBuilder<?> builder = new PathBuilderFactory().create(domainClass);
        Querydsl querydsl = new Querydsl(entityManager, builder);
        return querydsl;
    }


    /// QClass 를 통해 Querydsl 객체를 만든다.
    /// QClass 에 별칭을 사용했을 경우(기본 별칭이 아닌) 사용한다.
    @Nullable
    public static Querydsl getQuerydsl(EntityPath path,EntityManager entityManager) {
        PathBuilder<?> builder = new PathBuilder(path.getType(), path.getMetadata().getName());

        Querydsl querydsl = new Querydsl(entityManager, builder);
        return querydsl;
    }
}
