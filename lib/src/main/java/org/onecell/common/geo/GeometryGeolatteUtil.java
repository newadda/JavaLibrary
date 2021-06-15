package org.onecell.common.geo;


import org.geolatte.geom.*;
import org.geolatte.geom.codec.Wkt;
import org.geolatte.geom.codec.WktDecodeException;
import org.geolatte.geom.codec.WktDecoder;

import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.crs.CrsRegistry;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.io.ParseException;
import org.opengis.referencing.FactoryException;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;


public class GeometryGeolatteUtil {

    public static Polygon defaultPolygon() throws WktDecodeException {

        WktDecoder wktDecoder = Wkt.newDecoder();
        Geometry<?> decode = wktDecoder.decode("SRID=4326;POLYGON ((127.89376 37.67547, 127.89380 37.67547, 127.89380 37.67557,127.89376 37.67557, 127.89376 37.67547))");

        return (Polygon) decode;
    }

    public static Point defaultPoint() throws WktDecodeException {
        WktDecoder wktDecoder = Wkt.newDecoder();
        Geometry<?> decode = wktDecoder.decode("SRID=4326;POINT (128.089287 37.745933)");

        return (Point) decode;
    }

    public static Point createPoint(Double x,Double y,int SRID)
    {
        Point point =null;
        if(x==null || y==null)
        {
            point = Geometries.mkEmptyPoint(CrsRegistry.getProjectedCoordinateReferenceSystemForEPSG(SRID));
        }else
        {

            org.geolatte.geom.crs.CoordinateReferenceSystem crs = CrsRegistry.getCoordinateReferenceSystemForEPSG(SRID, CoordinateReferenceSystems.WGS84);
            Position cast = Positions.mkPosition(crs, x, y);
            point = Geometries.mkPoint(cast, crs);

        }

        return point;
    }


    public static Geometry  transform(Geometry geometry, int  dest_SRID) throws FactoryException, ParseException, TransformException {


        int srid = geometry.getSRID();
        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:"+srid);
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:"+dest_SRID);
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);

        org.locationtech.jts.geom.Geometry sourceGeometry = org.geolatte.geom.jts.JTS.to(geometry);
        org.locationtech.jts.geom.Geometry targetGeometry = JTS.transform( sourceGeometry, transform);
        targetGeometry.setSRID(dest_SRID);
        Geometry<?> from = org.geolatte.geom.jts.JTS.from(targetGeometry);

        return from;
    }

    public static Geometry  transform(MathTransform transform, Geometry geometry,int  dest_SRID) throws FactoryException, ParseException, TransformException {


        org.locationtech.jts.geom.Geometry sourceGeometry = org.geolatte.geom.jts.JTS.to(geometry);
        org.locationtech.jts.geom.Geometry targetGeometry = JTS.transform( sourceGeometry, transform);
        targetGeometry.setSRID(dest_SRID);
        Geometry<?> from = org.geolatte.geom.jts.JTS.from(targetGeometry);

        return from;
    }



}
