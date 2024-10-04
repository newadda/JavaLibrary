package org.onecell.common.querydsl.tibero;

import com.querydsl.spatial.SpatialOps;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spatial.PostGISTemplates;
import com.querydsl.sql.spatial.SpatialTemplatesSupport;

public class TiberoSpatialTemplates extends OracleTemplates {
    public static final PostGISTemplates DEFAULT = new PostGISTemplates();

    public static Builder builder() {
        return new Builder() {
            @Override
            protected SQLTemplates build(char escape, boolean quote) {
                return new PostGISTemplates(escape, quote);
            }
        };
    }

    public TiberoSpatialTemplates() {
        this('\\', false);
    }

    public TiberoSpatialTemplates(boolean quote) {
        this('\\', quote);
    }

    public TiberoSpatialTemplates(char escape, boolean quote) {
        super(escape, quote);
        addCustomType(TiberoJGeometryType.DEFAULT);
        add(SpatialTemplatesSupport.getSpatialOps(true)); // Tibero 가 PostgreSQL GIS 함수를 따르므로 기본 함수를 등록한다.

        // 실제 Postgresql 와 Spatial 함수가 비슷하지만 동일하지 않기 때문에 결국 구현해야 한다.
        add(SpatialOps.WITHIN,"(ST_Within({0},{1})= '1')"); // st_within 의 tibero 반환은 1 혹은 0 이다...
        add(SpatialOps.LENGTH,"(ST_Length({0})*111000)"); // M 단위로 바꾸기 위해 111000 을 곱한다.
        add(SpatialOps.INTERSECTS,"(ST_INTERSECTS({0},{1})= '1')");
    }
}
