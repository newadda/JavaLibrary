package org.onecell.common.querydsl;

import com.querydsl.spatial.SpatialOps;
import com.querydsl.sql.spatial.OracleSpatialTemplates;

public class OracleSpatialExtendsTemplates extends OracleSpatialTemplates {

    public OracleSpatialExtendsTemplates() {

        add(SpatialOps.DWITHIN,"" +
                " ( SDO_WITHIN_DISTANCE({0},{1},CONCAT(CONCAT('distance=', {2}),' unit=M'))= 'TRUE')");
        add(SpatialOps.WITHIN,"" +
                " ( SDO_INSIDE({0},{1})= 'TRUE')");
        add(SpatialOps.LENGTH,"" +
                " SDO_GEOM.SDO_Length({0},0.5)");
    }
}