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

public class OracleSpatialSDO12gExtendDialect extends OracleSpatial10gDialect {

    public static final String PREFER_LONG_RAW = "hibernate.dialect.oracle.prefer_long_raw";

        public OracleSpatialSDO12gExtendDialect() {
            super();
            getDefaultProperties().setProperty( Environment.BATCH_VERSIONED_DATA, "true" );
            registerFunction("ADD_HOURS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'HOUR') )"));
            registerFunction("ADD_DAYS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'DAY') )"));
            registerFunction("DWITHIN", new SQLFunctionTemplate(BooleanType.INSTANCE, "( SDO_WITHIN_DISTANCE(?1,?2,CONCAT(CONCAT('distance=', ?3),' unit=M'))= 'TRUE') AND 1"));
           // registerFunction("dwithin", new SQLFunctionTemplate(BooleanType.INSTANCE, "(SDO_WITHIN_DISTANCE(?1,?2,'distance=4 unit=M')='TRUE' )"));
            registerFunction("dayofyear", new SQLFunctionTemplate(IntegerType.INSTANCE, "  to_number(to_char(?1,'DDD'))")); // 년의 일수


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
