package org.onecell.common.geo;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class GeometryLocationtechUtil {

    public static Geometry CRSTrans(Geometry geometry,int to_srid) throws FactoryException, TransformException {
        int from_srid = geometry.getSRID();
        CoordinateReferenceSystem from_crs = CRS.decode("EPSG:" + from_srid);
        CoordinateReferenceSystem to_crs = CRS.decode("EPSG:" + to_srid);
        MathTransform transform = CRS.findMathTransform(from_crs, to_crs);

        Geometry to_Geometry = JTS.transform(geometry, transform);
        return to_Geometry;
    }
}
