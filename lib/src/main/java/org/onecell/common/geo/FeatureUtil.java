package org.onecell.common.geo;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;

public class FeatureUtil {
    // Geometry 가 GeometryCollection 일때 이중 첫번째 좌표만을 가져온다.
    public static <T extends Geometry> T findOneGeometryFromFeature(Class<T> clazz, Feature feature, int srid)
    {
        GeometryAttribute defaultGeometryProperty = feature.getDefaultGeometryProperty();
        Object geometry = defaultGeometryProperty.getValue();

        Geometry ret =null;

        if(geometry instanceof GeometryCollection)
        {
            GeometryCollection cast =   (GeometryCollection)geometry;
            ret = cast.getGeometryN(0);
        }else if( clazz.isInstance(geometry))
        {
            ret =   (T)geometry;

        }

        if(ret!=null)
        {
            ret.setSRID(srid);
        }

        return (T)ret;
    }

}
