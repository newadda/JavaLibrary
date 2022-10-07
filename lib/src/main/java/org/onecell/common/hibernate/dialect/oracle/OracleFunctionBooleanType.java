package org.onecell.common.hibernate.dialect.oracle;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.BooleanType;

public class OracleFunctionBooleanType extends BooleanType {
    public static final OracleFunctionBooleanType INSTANCE = new OracleFunctionBooleanType();

    @Override
    public String objectToSQLString(Boolean value, Dialect dialect) {
        return value ? "'TRUE'" : "'FALSE'";
    }
}
