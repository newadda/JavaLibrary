package org.onecell.example.geo.geojson;

import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class ObjectToFeatureConverter {
    public class FtrSuper {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ftr_id_seq")
        @SequenceGenerator(name="ftr_id_seq", sequenceName = "FTR_ID_SEQ",allocationSize = 1)
        @Column(name = "ID")
        Long id;

        @Column(name = "FTR_CDE")
        String ftr_cde;

        @Column(name = "FTR_IDN")
        long ftr_idn;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFtr_cde() {
            return ftr_cde;
        }

        public void setFtr_cde(String ftr_cde) {
            this.ftr_cde = ftr_cde;
        }

        public long getFtr_idn() {
            return ftr_idn;
        }

        public void setFtr_idn(long ftr_idn) {
            this.ftr_idn = ftr_idn;
        }
    }

    public class FtrWtlCldePs extends FtrSuper{
        @Column(name = "PIP_DEP")
        BigDecimal pip_dep; //심도

        @Column(name = "GEOM")
        org.geolatte.geom.Point geom;

        public BigDecimal getPip_dep() {
            return pip_dep;
        }

        public void setPip_dep(BigDecimal pip_dep) {
            this.pip_dep = pip_dep;
        }

        public org.geolatte.geom.Point getGeom() {
            return geom;
        }

        public void setGeom(org.geolatte.geom.Point geom) {
            this.geom = geom;
        }
    }

    final String NAME = "WTL_CLDE_PS";
    final String FTR_CDE = "FTR_CDE";
    final String FTR_IDN = "FTR_IDN";
    final String PIP_DEP = "PIP_DEP";


    public DefaultFeatureCollection toFeatureCollection(List<FtrWtlCldePs> listEntity, int SRID) throws FactoryException, SchemaException {

        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        CoordinateReferenceSystem decode = CRS.decode("EPSG:" + SRID);
        typeBuilder.setName(NAME);
        typeBuilder.setNamespaceURI(NAME);
        typeBuilder.setCRS(decode);
        typeBuilder.add(
                "geometry",
                Point.class);
        typeBuilder.add(FTR_CDE,String.class);
        typeBuilder.add(FTR_IDN,Long.class);
        typeBuilder.add(PIP_DEP,Double.class);
        typeBuilder.setDefaultGeometry("geometry");

        SimpleFeatureType featureType = typeBuilder.buildFeatureType();


        DefaultFeatureCollection featureCollection = new DefaultFeatureCollection(NAME,featureType);

        for(FtrWtlCldePs entity:listEntity)
        {
            SimpleFeature feature = toFeature(entity,featureType);
            featureCollection.add(feature);
        }

        return featureCollection;
    }


    public SimpleFeature toFeature(FtrWtlCldePs entity, SimpleFeatureType type) throws SchemaException, FactoryException {


        SimpleFeatureBuilder simpleFeatureBuilder = new SimpleFeatureBuilder(type);

        // geolatte 를 locationtech 로 변경
        org.locationtech.jts.geom.Geometry geometry = org.geolatte.geom.jts.JTS.to(entity.getGeom());
        simpleFeatureBuilder.set("geometry",geometry);
        simpleFeatureBuilder.set(FTR_CDE,entity.getFtr_cde());
        simpleFeatureBuilder.set(FTR_IDN,entity.getFtr_idn());
        simpleFeatureBuilder.set(PIP_DEP,entity.getPip_dep().doubleValue());

        SimpleFeature simpleFeature = simpleFeatureBuilder.buildFeature(entity.getId().toString());
        return simpleFeature;
    }
}
