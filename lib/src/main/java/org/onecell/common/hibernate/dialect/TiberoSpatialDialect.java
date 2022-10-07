package org.onecell.common.hibernate.dialect;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.spatial.*;
import org.hibernate.spatial.dialect.WithCustomJPAFilter;
import org.hibernate.type.*;

import org.onecell.common.hibernate.dialect.tibero.TiberoGeometryTypeDescriptor;
import org.onecell.common.hibernate.dialect.tibero.TiberoSupport;

import java.io.Serializable;
import java.util.Map;


public class TiberoSpatialDialect extends Oracle12cDialect implements SpatialDialect, WithCustomJPAFilter, Serializable {
    transient private TiberoSupport support = new TiberoSupport();

    /**
     * Creates an instance
     */
    public TiberoSpatialDialect() {
        super();


        registerColumnType(
                TiberoGeometryTypeDescriptor.INSTANCE.getSqlType(),
                "GEOMETRY"
        );

        for ( Map.Entry<String, SQLFunction> entry : support.functionsToRegister() ) {
            registerFunction( entry.getKey(), entry.getValue() );
        }

        /// Date Teim Function
        registerFunction("ADD_DAYS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'DAY') )"));
        registerFunction("ADD_HOURS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'HOUR') )"));

        registerFunction("dayofyear", new SQLFunctionTemplate(IntegerType.INSTANCE, "  to_number(to_char(?1,'DDD'))")); // 년의 일수

        /// Spatial Function
        registerFunction("DWITHIN", new SQLFunctionTemplate(BooleanType.INSTANCE, " (ST_DISTANCE( ?1  ,?2) *111000 <= ?3) and 1"));

        //registerFunction("WITHIN", new SQLFunctionTemplate(BooleanType.INSTANCE, " (ST_Within( ?1  ,?2)= '1') and 1")); // true 만 처리된다. 그래서 아래를 사용한다.
        registerFunction("WITHIN",  new StandardSQLFunction("ST_Within", StandardBasicTypes.BOOLEAN));
        registerFunction("LENGTH", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "(ST_Length(?1)*111000)"));
        registerFunction("CONTAINS",  new StandardSQLFunction("ST_Contains", StandardBasicTypes.BOOLEAN));

    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes(
                typeContributions,
                serviceRegistry
        );



        typeContributions.contributeType( new GeolatteGeometryType( TiberoGeometryTypeDescriptor.INSTANCE ) );
        typeContributions.contributeType( new JTSGeometryType( TiberoGeometryTypeDescriptor.INSTANCE ) );

        typeContributions.contributeJavaTypeDescriptor( GeolatteGeometryJavaTypeDescriptor.INSTANCE );
        typeContributions.contributeJavaTypeDescriptor( JTSGeometryJavaTypeDescriptor.INSTANCE );
    }

    /**
     * Returns the SQL fragment for the SQL WHERE-clause when parsing
     * <code>org.hibernatespatial.criterion.SpatialRelateExpression</code>s
     * into prepared statements.
     * <p/>
     *
     * @param columnName The name of the geometry-typed column to which the relation is
     * applied
     * @param spatialRelation The type of spatial relation (as defined in
     * <code>SpatialRelation</code>).
     *
     * @return SQL fragment  {@code SpatialRelateExpression}
     */
    @Override
    public String getSpatialRelateSQL(String columnName, int spatialRelation) {
        return support.getSpatialRelateSQL( columnName, spatialRelation );
    }

    /**
     * Returns the SQL fragment for the SQL WHERE-expression when parsing
     * <code>org.hibernate.spatial.criterion.SpatialFilterExpression</code>s
     * into prepared statements.
     *
     * @param columnName The name of the geometry-typed column to which the filter is
     * be applied
     *
     * @return Rhe SQL fragment for the {@code SpatialFilterExpression}
     */
    @Override
    public String getSpatialFilterExpression(String columnName) {
        return support.getSpatialFilterExpression( columnName );
    }

    /**
     * Returns the SQL fragment for the specfied Spatial aggregate expression.
     *
     * @param columnName The name of the Geometry property
     * @param aggregation The type of <code>SpatialAggregate</code>
     *
     * @return The SQL fragment for the projection
     */
    @Override
    public String getSpatialAggregateSQL(String columnName, int aggregation) {
        return support.getSpatialAggregateSQL( columnName, aggregation );
    }

    /**
     * Returns The SQL fragment when parsing a <code>DWithinExpression</code>.
     *
     * @param columnName The geometry column to test against
     *
     * @return The SQL fragment when parsing a <code>DWithinExpression</code>.
     */
    @Override
    public String getDWithinSQL(String columnName) {
        return support.getDWithinSQL( columnName );
    }

    /**
     * Returns the SQL fragment when parsing a <code>HavingSridExpression</code>.
     *
     * @param columnName The geometry column to test against
     *
     * @return The SQL fragment for a <code>HavingSridExpression</code>.
     */
    @Override
    public String getHavingSridSQL(String columnName) {
        return support.getHavingSridSQL( columnName );
    }

    /**
     * Returns the SQL fragment when parsing a <code>IsEmptyExpression</code> or
     * <code>IsNotEmpty</code> expression.
     *
     * @param columnName The geometry column
     * @param isEmpty Whether the geometry is tested for empty or non-empty
     *
     * @return The SQL fragment for the isempty function
     */
    @Override
    public String getIsEmptySQL(String columnName, boolean isEmpty) {
        return support.getIsEmptySQL( columnName, isEmpty );
    }

    /**
     * Returns true if this <code>SpatialDialect</code> supports a specific filtering function.
     * <p> This is intended to signal DB-support for fast window queries, or MBR-overlap queries.</p>
     *
     * @return True if filtering is supported
     */
    @Override
    public boolean supportsFiltering() {
        return support.supportsFiltering();
    }

    /**
     * Does this dialect supports the specified <code>SpatialFunction</code>.
     *
     * @param function <code>SpatialFunction</code>
     *
     * @return True if this <code>SpatialDialect</code> supports the spatial function specified by the function parameter.
     */
    @Override
    public boolean supports(SpatialFunction function) {
        return support.supports( function );
    }

    @Override
    public String filterExpression(String geometryParam, String filterParam) {


        return null;
    }
}
