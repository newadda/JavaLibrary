package org.onecell.example.jpa.entity.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="WTWS_BLOCK_NIGHT_SETTING")
public class BlockNightSetting {

    @Id
    @Column(name = "BLOCK_ID",columnDefinition="NUMBER(19)")
    Long block_id;

    @Column(name = "NIGHT_START_HH")
    Integer night_start_hh=01;
    @Column(name = "NIGHT_START_MI")
    Integer night_start_mi=00;
    @Column(name = "NIGHT_END_HH")
    Integer night_end_hh=05;
    @Column(name = "NIGHT_END_MI")
    Integer night_end_mi=00;
    @Column(name = "NIGHT_MIN_FLOW_VA")
    BigDecimal night_min_flow_va=new BigDecimal(999999);

    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
    }

    public Integer getNight_start_hh() {
        return night_start_hh;
    }

    public void setNight_start_hh(Integer night_start_hh) {
        this.night_start_hh = night_start_hh;
    }

    public Integer getNight_start_mi() {
        return night_start_mi;
    }

    public void setNight_start_mi(Integer night_start_mi) {
        this.night_start_mi = night_start_mi;
    }

    public Integer getNight_end_hh() {
        return night_end_hh;
    }

    public void setNight_end_hh(Integer night_end_hh) {
        this.night_end_hh = night_end_hh;
    }

    public Integer getNight_end_mi() {
        return night_end_mi;
    }

    public void setNight_end_mi(Integer night_end_mi) {
        this.night_end_mi = night_end_mi;
    }

    public BigDecimal getNight_min_flow_va() {
        return night_min_flow_va;
    }

    public void setNight_min_flow_va(BigDecimal night_min_flow_va) {
        this.night_min_flow_va = night_min_flow_va;
    }
}
