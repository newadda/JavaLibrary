package org.onecell.common.geo;


import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.locationtech.proj4j.*;


public class GeometryVividsolutionsUtil {
    private static CRSFactory factory = new CRSFactory();
    private static GeometryFactory geometryFactory = new GeometryFactory();

    public static Geometry wktToGeometry(String wkt) throws ParseException {
        return new WKTReader().read(wkt);


    }
    public static String geometryToWkt(Geometry geometry)
    {
        return  new WKTWriter().write(geometry);
    }

    // Geometry 좌표변환
    public static Geometry GeometryTransform(Geometry geometry, int srcSRID, int dstSRID)
    {
        Geometry retGeometry=null;
        if(geometry instanceof Point)
        {
            Coordinate coordinate = geometry.getCoordinate();
            Coordinate transCoordinate = coordinateTransform(coordinate, srcSRID, dstSRID);
            retGeometry = geometryFactory.createPoint(transCoordinate);

        }else if(geometry instanceof Polygon || geometry instanceof LineString || geometry instanceof Polygon)
        {



        }

        if(retGeometry!=null)
        {
            retGeometry.setSRID(dstSRID);
        }

        return retGeometry;
    }

    public static Coordinate coordinateTransform(Coordinate coordinate, int srcSRID, int dstSRID)
    {
        CoordinateReferenceSystem srcCrs = factory.createFromName("EPSG:" + srcSRID);
        CoordinateReferenceSystem dstCrs = factory.createFromName("EPSG:" + dstSRID);
        BasicCoordinateTransform transform = new BasicCoordinateTransform(srcCrs, dstCrs);

        org.locationtech.proj4j.CoordinateTransformFactory a = new CoordinateTransformFactory();



        ProjCoordinate srcCoord = coordinateToProjCoordinate(coordinate);
        ProjCoordinate dstCoord = new ProjCoordinate();

        transform.transform(srcCoord,dstCoord);
        return projCoordinateToCoordinate(dstCoord);
    }

    private static ProjCoordinate coordinateToProjCoordinate(Coordinate coordinate)
    {
        return new ProjCoordinate(coordinate.x,coordinate.y,coordinate.z);
    }

    private static Coordinate projCoordinateToCoordinate(ProjCoordinate projCoordinate)
    {
        return  new Coordinate(projCoordinate.x,projCoordinate.y,projCoordinate.z);
    }



    public static Polygon defaultPolygon()
    {
        try {
            return (Polygon) new WKTReader().read("POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
