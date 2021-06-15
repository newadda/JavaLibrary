package org.onecell.example.jpa.entity.etc;

import org.geolatte.geom.Point;
import org.hibernate.annotations.Nationalized;
import org.waterworks.lib.db.entity.etc.pk.AddressCoord_PK;

import javax.persistence.*;

@Entity
@Table(name="WTWS_ADDRESS_COORD")
@IdClass(AddressCoord_PK.class)
public class AddressCoord {


    @Id
    @Nationalized
    @Column(name="ROAD_CODE") protected String road_code;

    @Id
    @Nationalized @Column(name="IS_UNDERGROUND") protected String is_underground;

    @Id
    @Column(name="BUILDING_ORIGIN_NUMBER") protected Integer building_origin_number;

    @Id
    @Column(name="BUILDING_PART_NUMBER") protected Integer building_part_number;

    @Id
    @Nationalized @Column(name="BEOPJEONGDONG_CODE") protected String beopjeongdong_code;

    @Nationalized
    @Column(name="SI_GUN_GU_CODE")
    private String si_gun_gu_code;

    @Nationalized
    @Column(name="DOOR_NUMBER")
    private String door_number;

    @Nationalized
    @Column(name="SIDO_NAME")
    private String sido_name;

    @Nationalized
    @Column(name="SI_GUN_GU_NAME")
    private String si_gun_gu_name;

    @Nationalized
    @Column(name="EUP_MUN_DONG_NAME")
    private String eup_mun_dong_name;

    @Nationalized
    @Column(name="ROAD_NAME")
    private String road_name;

    @Nationalized
    @Column(name="BUILDING_NAME")
    private String building_name;

    @Nationalized
    @Column(name="ZIP_CODE")
    private String zip_code;

    @Nationalized
    @Column(name="BUILDING_USAGE_TYPE")
    private String building_usage_type;

    @Nationalized
    @Column(name="BUILDING_CATEGORY_TYPE")
    private String building_category_type;

    @Nationalized
    @Column(name="HAENGJEONGDONG")
    private String haengjeongdong;

    /// 좌표계는 GRS80 UTM-K   EPSG:5179 Korea 2000 / Unified CS 사용한다.

    @Column(name="ENT_X")
    private Double ent_x;

    @Column(name="ENT_Y")
    private Double ent_y;


    @Column(name = "GEOM")
    Point geom;

    @Transient
    private String move_reason_code;


    public AddressCoord_PK getId()
    {
        return  new AddressCoord_PK(this.getRoad_code(),this.getIs_underground(),this.getBuilding_origin_number(),this.getBuilding_part_number(),this.getBeopjeongdong_code());
    }

    public String getSi_gun_gu_code() {
        return si_gun_gu_code;
    }

    public void setSi_gun_gu_code(String si_gun_gu_code) {
        this.si_gun_gu_code = si_gun_gu_code;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getSido_name() {
        return sido_name;
    }

    public void setSido_name(String sido_name) {
        this.sido_name = sido_name;
    }

    public String getSi_gun_gu_name() {
        return si_gun_gu_name;
    }

    public void setSi_gun_gu_name(String si_gun_gu_name) {
        this.si_gun_gu_name = si_gun_gu_name;
    }

    public String getEup_mun_dong_name() {
        return eup_mun_dong_name;
    }

    public void setEup_mun_dong_name(String eup_mun_dong_name) {
        this.eup_mun_dong_name = eup_mun_dong_name;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getBuilding_usage_type() {
        return building_usage_type;
    }

    public void setBuilding_usage_type(String building_usage_type) {
        this.building_usage_type = building_usage_type;
    }

    public String getBuilding_category_type() {
        return building_category_type;
    }

    public void setBuilding_category_type(String building_category_type) {
        this.building_category_type = building_category_type;
    }

    public String getHaengjeongdong() {
        return haengjeongdong;
    }

    public void setHaengjeongdong(String haengjeongdong) {
        this.haengjeongdong = haengjeongdong;
    }


    public Double getEnt_x() {
        return ent_x;
    }

    public void setEnt_x(Double ent_x) {
        this.ent_x = ent_x;
    }

    public Double getEnt_y() {
        return ent_y;
    }

    public void setEnt_y(Double ent_y) {
        this.ent_y = ent_y;
    }

    public String getMove_reason_code() {
        return move_reason_code;
    }

    public void setMove_reason_code(String move_reason_code) {
        this.move_reason_code = move_reason_code;
    }

    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom) {
        this.geom = geom;
    }

    public String getRoad_code() {
        return road_code;
    }

    public void setRoad_code(String road_code) {
        this.road_code = road_code;
    }

    public String getIs_underground() {
        return is_underground;
    }

    public void setIs_underground(String is_underground) {
        this.is_underground = is_underground;
    }

    public Integer getBuilding_origin_number() {
        return building_origin_number;
    }

    public void setBuilding_origin_number(Integer building_origin_number) {
        this.building_origin_number = building_origin_number;
    }

    public Integer getBuilding_part_number() {
        return building_part_number;
    }

    public void setBuilding_part_number(Integer building_part_number) {
        this.building_part_number = building_part_number;
    }

    public String getBeopjeongdong_code() {
        return beopjeongdong_code;
    }

    public void setBeopjeongdong_code(String beopjeongdong_code) {
        this.beopjeongdong_code = beopjeongdong_code;
    }
}
