package org.onecell.common.geo;

import org.geolatte.geom.*;
import org.geolatte.geom.codec.Wkt;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.crs.CrsId;
import org.geolatte.geom.crs.CrsRegistry;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

// Geometry 관련 통합 유틸
public class GeometryUtil {

    private static final int DEFAULT_SRID = 4326;

    static{

        // geotools epsg 등록 초기화



    }

    public static org.geolatte.geom.Geometry boundaryBox(double minC1,double minC2,double maxC1,double maxC2,int srid )
    {

        org.geolatte.geom.crs.CoordinateReferenceSystem<?> crs
                = CrsRegistry.getCoordinateReferenceSystemForEPSG(srid, CoordinateReferenceSystems.WGS84);

         Envelope env=new Envelope(minC1,minC2,maxC1,maxC2,crs );
        Class positionClass = env.getCoordinateReferenceSystem().getPositionClass();
        PositionSequence ps = PositionSequenceBuilders.fixedSized(4, positionClass)
                .add(env.lowerLeft().getCoordinate(0), env.lowerLeft().getCoordinate(1))
                .add(env.lowerLeft().getCoordinate(0), env.upperRight().getCoordinate(1))
                .add(env.upperRight().getCoordinate(0), env.upperRight().getCoordinate(1))
                .add(env.lowerLeft().getCoordinate(0), env.lowerLeft().getCoordinate(1))
                .toPositionSequence();
        LinearRing linearRing = Geometries.mkLinearRing(ps, crs);
        Polygon polygon = Geometries.mkPolygon(linearRing);
        return polygon;
    }



    public static String WKTEncoding(org.geolatte.geom.Geometry geometry)
    {
        if(ObjectUtils.isEmpty(geometry))
        {
            return null;
        }
        Geometry to = org.geolatte.geom.jts.JTS.to(geometry);
        WKTWriter wktWriter = new WKTWriter();
        String wkt =  wktWriter.write(to);
        return wkt;
    }

    public static <T> T WKTDecoding(Class<T> clazz,String wkt)
    {
        return WKTDecoding(clazz,wkt,DEFAULT_SRID);
    }
    // wkt 의 현재 SRID 이다.
    public static <T> T WKTDecoding(Class<T> clazz,String wkt,int srid)
    {
        if(StringUtils.isEmpty(wkt))
        {
            return null;
        }

        if( org.geolatte.geom.Geometry.class.isAssignableFrom(clazz))
        {
            CrsId crsIdForEPSG = CrsRegistry.getCrsIdForEPSG(srid);
            org.geolatte.geom.crs.CoordinateReferenceSystem<?> coordinateReferenceSystem = CrsRegistry.getCoordinateReferenceSystem(crsIdForEPSG, CoordinateReferenceSystems.WGS84);

            org.geolatte.geom.Geometry<?> geometry = Wkt.fromWkt(wkt,coordinateReferenceSystem);
            return (T)geometry;
        }
        return null;
    }


    public static org.geolatte.geom.Geometry  transform(org.geolatte.geom.Geometry geometry,int source_SRID, int  dest_SRID) throws FactoryException, ParseException, TransformException {

        String wkt = Wkt.toWkt(geometry);

        if(wkt.contains(";"))
        {
            String[] split = wkt.split(";");
            wkt=split[1];
        }

        String transfrom = transfrom(wkt, source_SRID, dest_SRID);
        transfrom="SRID="+dest_SRID+";"+transfrom;

        org.geolatte.geom.Geometry<?> geometry1 = Wkt.fromWkt(transfrom);


        return geometry1;
    }


    public static String transfrom(String wkt,int source_SRID, int  dest_SRID ) throws FactoryException, ParseException, TransformException {


        WKTReader reader = new WKTReader();
        Geometry sourceGeometry = reader.read(wkt);

        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:"+source_SRID);
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:"+dest_SRID);
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
        Geometry targetGeometry = JTS.transform( sourceGeometry, transform);

        WKTWriter wktWriter = new WKTWriter();
        String write = wktWriter.write(targetGeometry);
        return write;
    }

    public Polygon  bboxToPolygon(double minc1, double minc2, double maxc1, double maxc2,int srid)
    {
        org.geolatte.geom.crs.CoordinateReferenceSystem<?> crs = CrsRegistry.getCoordinateReferenceSystemForEPSG(srid, CoordinateReferenceSystems.WGS84);

        Envelope env=new Envelope(minc1,minc2,maxc1,maxc2,crs);
        PositionSequence ps = PositionSequenceBuilders.fixedSized(4, crs.getPositionClass())
                .add(env.lowerLeft().getCoordinate(0), env.lowerLeft().getCoordinate(1))
                .add(env.lowerLeft().getCoordinate(0), env.upperRight().getCoordinate(1))
                .add(env.upperRight().getCoordinate(0), env.upperRight().getCoordinate(1))
                .add(env.lowerLeft().getCoordinate(0), env.lowerLeft().getCoordinate(1))
                .toPositionSequence();
        LinearRing linearRing = Geometries.mkLinearRing(ps, crs);
        Polygon polygon = Geometries.mkPolygon(linearRing);
        return polygon;
    }



}
