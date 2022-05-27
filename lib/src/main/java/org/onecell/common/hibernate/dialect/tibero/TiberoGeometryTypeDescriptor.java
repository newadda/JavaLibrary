package org.onecell.common.hibernate.dialect.tibero;

import org.geolatte.geom.Geometry;
import org.geolatte.geom.jts.JTS;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

import java.sql.*;

public class TiberoGeometryTypeDescriptor implements SqlTypeDescriptor {

    /**
     * An instance of this Descriptor
     */
    public static final TiberoGeometryTypeDescriptor INSTANCE = new TiberoGeometryTypeDescriptor();

    @Override
    public int getSqlType() {
        return Types.BLOB;
    }

    @Override
    public boolean canBeRemapped() {
        return false;
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>( javaTypeDescriptor, this ) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
                    throws SQLException {
               ;
               // final WkbEncoder encoder = Wkb.newEncoder( Wkb.Dialect.POSTGIS_EWKB_1 );
                final Geometry geometry = getJavaDescriptor().unwrap( value, Geometry.class, options );
                //final ByteBuffer buffer = encoder.encode( geometry, ByteOrder.NDR );
               // final ByteBuffer buffer2 = encoder.encode( geometry, ByteOrder.XDR );
               // final byte[] bytes = ( buffer == null ? null : buffer.toByteArray() );
                byte[] bytes=null;
                if(geometry!=null) {

                    final WKBWriter encoder = new WKBWriter();
                    final org.locationtech.jts.geom.Geometry to = JTS.to(geometry);

                    bytes = encoder.write(to);
                }
                st.setBytes( index, bytes );
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                //final WkbEncoder encoder = Wkb.newEncoder( Wkb.Dialect.POSTGIS_EWKB_1 );
                final Geometry geometry = getJavaDescriptor().unwrap( value, Geometry.class, options );
                //final ByteBuffer buffer = encoder.encode( geometry, ByteOrder.NDR );
                //final byte[] bytes = ( buffer == null ? null : buffer.toByteArray() );

                byte[] bytes=null;
                if(geometry!=null)
                {
                    final  WKBWriter encoder = new WKBWriter();
                    final org.locationtech.jts.geom.Geometry to = JTS.to(geometry);
                    bytes = encoder.write(to);
                }

                st.setBytes( name, bytes );
            }
        };
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>( javaTypeDescriptor, this ) {

            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                return getJavaDescriptor().wrap( toGeometry( rs.getBytes( name ) ), options );
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return getJavaDescriptor().wrap( toGeometry( statement.getBytes( index ) ), options );
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
                    throws SQLException {
                return getJavaDescriptor().wrap( toGeometry( statement.getBytes( name ) ), options );
            }
        };
    }

    private Geometry toGeometry(byte[] bytes) {
        if ( bytes == null ) {
            return null;
        }
       // final ByteBuffer buffer = ByteBuffer.from( bytes );
       // final WkbDecoder decoder = Wkb.newDecoder( Wkb.Dialect.POSTGIS_EWKB_1 );
        //return decoder.decode( buffer );

        final  WKBReader decoder = new WKBReader();
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
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

}