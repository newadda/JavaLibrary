package org.onecell.common.hibernate.dialect.tibero;

import com.tmax.tibero.jdbc.TbBlob;
import org.geolatte.geom.ByteBuffer;
import org.geolatte.geom.ByteOrder;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.codec.Wkb;
import org.geolatte.geom.codec.WkbDecoder;
import org.geolatte.geom.codec.WkbEncoder;
import org.geolatte.geom.crs.CrsRegistry;
import org.geolatte.geom.jts.JTS;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.spatial.dialect.mysql.MySQLGeometryTypeDescriptor;
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
import org.locationtech.jts.io.WKTWriter;
import org.onecell.common.hibernate.dialect.tibero.TiberoGeometryTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;


public class TiberoGeometryTypeDescriptorV2 implements SqlTypeDescriptor {
    private static final Logger log= LoggerFactory.getLogger(TiberoGeometryTypeDescriptorV2.class);
    private int type = Types.BLOB;
    /**
     * An instance of this Descriptor
     */
    public static final TiberoGeometryTypeDescriptorV2 INSTANCE = new TiberoGeometryTypeDescriptorV2();
    public static final TiberoGeometryTypeDescriptorV2 INSTANCE2 = new TiberoGeometryTypeDescriptorV2(26);
    public TiberoGeometryTypeDescriptorV2() {

    }
    public TiberoGeometryTypeDescriptorV2(int type) {
        this.type = type;
    }

    @Override
    public int getSqlType() {
        return type;

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

                log.debug("doBind(PreparedStatement st, X value, int index, WrapperOptions options)");
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

                // ==== BUILD_NUMBER	 : 199301 에서  The LOB locator has not been initialized. 오류 발생으로 코드 수정
                Blob blob = null;
                try {

                    blob = st.getConnection().createBlob();
                    blob.setBytes(1,bytes);
                    st.setBlob(index, blob);

                }catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                finally {
                    //blob.free(); // free() 하면안됨. 파라미터쓰기전에 blob 데이터 없앰
                }
                // ====

                log.debug("before  st.setBytes( index, bytes )");
                ///// BUILD_NUMBER	 : 199301 이전에 사용하던거.
                //st.setBytes( index, bytes );
                log.debug("after  st.setBytes( index, bytes )");

            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                //final WkbEncoder encoder = Wkb.newEncoder( Wkb.Dialect.POSTGIS_EWKB_1 );
                log.debug("doBind(CallableStatement st, X value, String name, WrapperOptions options)");
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

                // ==== BUILD_NUMBER	 : 199301 에서  The LOB locator has not been initialized. 오류 발생으로 코드 수정
                Blob blob = null;
                try {

                    blob = st.getConnection().createBlob();
                    blob.setBytes(1,bytes);
                    st.setBlob(name, blob);

                }catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                finally {
                    //blob.free(); // free() 하면안됨. 파라미터쓰기전에 blob 데이터 없앰
                }
                // ====

                log.debug("before  st.setBytes( index, bytes )");
                ///// BUILD_NUMBER	 : 199301 이전에 사용하던거.
                //st.setBytes( name, bytes );
                log.debug("after  st.setBytes( index, bytes )");
            }
        };
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>( javaTypeDescriptor, this ) {

            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                log.debug("doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException");

                Object object = rs.getObject(name);
                if(TbBlob.class.isInstance(object))
                {

                    TbBlob tbBlob = (TbBlob)object;

                    return getJavaDescriptor().wrap( toGeometry( tbBlob.getBytes(1,(int)tbBlob.length()) ), options );
                }else {
                    return getJavaDescriptor().wrap( toGeometry( rs.getBytes( name ) ), options );
                }

            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                log.debug("doExtract(CallableStatement statement, int index, WrapperOptions options)");


                Object object = statement.getObject(index);
                if(TbBlob.class.isInstance(object))
                {

                    TbBlob tbBlob = (TbBlob)object;

                    return getJavaDescriptor().wrap( toGeometry( tbBlob.getBytes(1,(int)tbBlob.length()) ), options );
                }else {
                    return getJavaDescriptor().wrap( toGeometry( statement.getBytes( index ) ), options );
                }



            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
                    throws SQLException {
                log.debug("doExtract(CallableStatement statement, String name, WrapperOptions options)");

                Object object = statement.getObject(name);
                if(TbBlob.class.isInstance(object))
                {

                    TbBlob tbBlob = (TbBlob)object;

                    return getJavaDescriptor().wrap( toGeometry( tbBlob.getBytes(1,(int)tbBlob.length()) ), options );
                }else {
                    return getJavaDescriptor().wrap( toGeometry( statement.getBytes( name ) ), options );
                }
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