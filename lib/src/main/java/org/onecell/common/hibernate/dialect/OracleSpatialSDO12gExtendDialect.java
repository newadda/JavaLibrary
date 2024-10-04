package org.onecell.common.hibernate.dialect;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.Oracle12cIdentityColumnSupport;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.config.spi.StandardConverters;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.spatial.dialect.oracle.OracleSpatial10gDialect;
import org.hibernate.type.*;
import org.onecell.common.hibernate.dialect.oracle.OracleFunctionBooleanType;

public class OracleSpatialSDO12gExtendDialect extends OracleSpatial10gDialect {

    public static final String PREFER_LONG_RAW = "hibernate.dialect.oracle.prefer_long_raw";

        public OracleSpatialSDO12gExtendDialect() {
            super();
            getDefaultProperties().setProperty( Environment.BATCH_VERSIONED_DATA, "true" );

            /// Date Teim Function
            registerFunction("ADD_HOURS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'HOUR') )"));
            registerFunction("ADD_DAYS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'DAY') )"));
            registerFunction("dayofyear", new SQLFunctionTemplate(IntegerType.INSTANCE, "  to_number(to_char(?1,'DDD'))")); // 년의 일수

            registerFunction("trunc_day", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(TRUNC(?1,'DD') )"));

            /// Spatial Function
            // registerFunction("dwithin", new SQLFunctionTemplate(BooleanType.INSTANCE, "(SDO_WITHIN_DISTANCE(?1,?2,'distance=4 unit=M')='TRUE' )"));
            registerFunction("DWITHIN", new SQLFunctionTemplate(BooleanType.INSTANCE, "( SDO_WITHIN_DISTANCE(?1,?2,CONCAT(CONCAT('distance=', ?3),' unit=M'))= 'TRUE') AND 1"));
            registerFunction("WITHIN", new SQLFunctionTemplate(BooleanType.INSTANCE, " ( SDO_INSIDE(?1,?2)= 'TRUE')  AND 1"));
            registerFunction("LENGTH", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, " SDO_GEOM.SDO_Length(?1,0.5)"));

            // Oracle Function 은 'TRUE' 를 반환한다. 그래서 Oracle 전용 BooleanType을 만들었다.
            registerFunction("CONTAINS", new SQLFunctionTemplate(OracleFunctionBooleanType.INSTANCE, "SDO_CONTAINS(?1,?2)"));
            registerFunction("INTERSECTS", new SQLFunctionTemplate(OracleFunctionBooleanType.INSTANCE, "SDO_ANYINTERACT(?1,?2)"));
        }


    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes( typeContributions, serviceRegistry );

        // account for Oracle's deprecated support for LONGVARBINARY...
        // 		prefer BLOB, unless the user opts out of it
        boolean preferLong = serviceRegistry.getService( ConfigurationService.class ).getSetting(
                PREFER_LONG_RAW,
                StandardConverters.BOOLEAN,
                false
        );

        if ( !preferLong ) {
            typeContributions.contributeType( MaterializedBlobType.INSTANCE, "byte[]", byte[].class.getName() );
            typeContributions.contributeType( WrappedMaterializedBlobType.INSTANCE, "Byte[]", Byte[].class.getName() );
        }
    }

    @Override
    protected void registerDefaultProperties() {
        super.registerDefaultProperties();
        getDefaultProperties().setProperty( Environment.USE_GET_GENERATED_KEYS, "true" );
    }

    @Override
    public String getNativeIdentifierGeneratorStrategy() {
        return "sequence";
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new Oracle12cIdentityColumnSupport();
    }


}
