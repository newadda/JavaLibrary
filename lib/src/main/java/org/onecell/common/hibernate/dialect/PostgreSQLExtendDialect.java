package org.onecell.common.hibernate.dialect;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.LocalDateTimeType;


public class PostgreSQLExtendDialect extends PostgreSQL95Dialect {
    public PostgreSQLExtendDialect() {
        super();

        registerFunction("TO_TIMESTAMP_HOUR", new SQLFunctionTemplate(
                LocalDateTimeType.INSTANCE,"TO_TIMESTAMP(?1,'YYYYMMDDHH24')"
        ));


        registerFunction("TO_TIMESTAMP_MINUTE", new SQLFunctionTemplate(
                LocalDateTimeType.INSTANCE,"TO_TIMESTAMP(?1,'YYYYMMDDHH24MI')"
        ));


        registerFunction("TO_TIMESTAMP_SECOND", new SQLFunctionTemplate(
                LocalDateTimeType.INSTANCE,"TO_TIMESTAMP(?1,'YYYYMMDDHH24MISS')"
        ));

        /// TRUNC_HOUR
        registerFunction("TRUNC_HOUR", new SQLFunctionTemplate(
                LocalDateTimeType.INSTANCE,"date_trunc('hour',?1)"
        ));

    }
}
