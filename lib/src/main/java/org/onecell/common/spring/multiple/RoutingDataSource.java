package org.onecell.common.spring.multiple;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/// 기존 DataSource 대신 사용하면 된다.
/// lookupkey 를 통해 DataSource 가 바뀌는 개념이다.
/// Hibernate 와 사용시 같은 종류의 DB에 대해서만 가능하다.
/// 왜냐면 SessionFactory 입장에서는 DataSource 가 하나이기 때문이다.
public class RoutingDataSource extends AbstractRoutingDataSource {
    DBKeyContext context;


    public RoutingDataSource(DBKeyContext container)
    {
        this.context =container;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return context.get();
    }


}
