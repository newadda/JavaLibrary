package org.onecell.common.querydsl.tibero;


import com.querydsl.sql.types.AbstractType;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.jts.JTS;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class TiberoJGeometryType extends AbstractType<Geometry> {

    public static final TiberoJGeometryType DEFAULT = new TiberoJGeometryType();

    public TiberoJGeometryType() {
        super(Types.BLOB);
    }

    @Override
    public Class<Geometry> getReturnedClass() {
        return Geometry.class;
    }

    @Override
    @Nullable
    public Geometry getValue(ResultSet rs, int startIndex) throws SQLException {
        byte[] bytes = rs.getBytes(startIndex);
        if (bytes != null) {
            final WKBReader decoder = new WKBReader();
            try {
                final org.locationtech.jts.geom.Geometry read = decoder.read(bytes);

                /// 티베로의 경우 geometry type에 srid 가 따로 저장되지 않는다.
                /// 나의 경우는 기본적으로 4326을 사용하므로 아래와 같이 srid 를 설정했다.
                if(read.getSRID()==0)
                {
                    read.setSRID(4326);
                }
                final Geometry<?> from = JTS.from(read);

                return from;
            } catch (Exception e) {
                throw new SQLException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public void setValue(PreparedStatement st, int startIndex, Geometry value) throws SQLException {
        try {
            byte[] bytes=null;
            if(value!=null)
            {
                final WKBWriter encoder = new WKBWriter();
                final org.locationtech.jts.geom.Geometry to = JTS.to(value);
                bytes = encoder.write(to);
            }

            st.setBytes( startIndex, bytes );
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
