package org.onecell.example.jpa.entity.etc.pk;

import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import java.io.Serializable;

public class AddressCoord_PK   implements Serializable {
    private static final long serialVersionUID = 1L;

    @Nationalized
    @Column(name="ROAD_CODE") protected String road_code;
    @Nationalized @Column(name="IS_UNDERGROUND") protected String is_underground;
    @Column(name="BUILDING_ORIGIN_NUMBER") protected Integer building_origin_number;
    @Column(name="BUILDING_PART_NUMBER") protected Integer building_part_number;
    @Nationalized @Column(name="BEOPJEONGDONG_CODE") protected String beopjeongdong_code;


    public AddressCoord_PK() {
        // TODO Auto-generated constructor stub
    }

    public AddressCoord_PK(String road_code, String is_underground, Integer building_origin_number, Integer building_part_number, String beopjeongdong_code) {
        this.road_code = road_code;
        this.is_underground = is_underground;
        this.building_origin_number = building_origin_number;
        this.building_part_number = building_part_number;
        this.beopjeongdong_code = beopjeongdong_code;
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof AddressCoord_PK) && road_code == ((AddressCoord_PK)o).getRoad_code() && is_underground == ((AddressCoord_PK) o).getIs_underground()&& building_origin_number == ((AddressCoord_PK)o).getBuilding_origin_number()&& building_part_number == ((AddressCoord_PK)o).getBuilding_part_number()&& beopjeongdong_code == ((AddressCoord_PK)o).getBeopjeongdong_code());
    }
    @Override
    public int hashCode() {
        return (int)(road_code.hashCode() ^ is_underground.hashCode() ^ building_origin_number.hashCode() ^ building_part_number.hashCode() ^ beopjeongdong_code.hashCode());
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
