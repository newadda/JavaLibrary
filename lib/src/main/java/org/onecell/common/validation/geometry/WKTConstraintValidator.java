package org.onecell.common.validation.geometry;

import org.geotools.geometry.jts.WKTReader2;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WKTConstraintValidator implements ConstraintValidator<WKT, String> {
    WKTType type;
    @Override
    public void initialize(WKT constraintAnnotation) {
        type = constraintAnnotation.type();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null || value.trim().isEmpty()==true)
        {
            return true;
        }

        WKTReader2 wktReader = new WKTReader2();
        try {
            Geometry read = wktReader.read(value);
            if(type == WKTType.POINT)
            {
                if(read instanceof Point)
                {
                    return true;
                }
            }else if(type == WKTType.POLYGON)
            {
                if(read instanceof Polygon)
                {
                    return true;
                }
            }

            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
