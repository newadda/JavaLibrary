package org.onecell.example.jpa.entity.block;



import org.geolatte.geom.Polygon;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;


@Entity
@Table(name="WTWS_BLOCK")
public class BlockDetail extends ABlock {


    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID",referencedColumnName="BLOCK_ID",nullable = false,updatable = false,insertable = false)
    BlockTagSetting blockTagSetting;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID",referencedColumnName="BLOCK_ID",nullable = false,updatable = false,insertable = false)
    BlockNightSetting blockNightSetting;


    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID",referencedColumnName="BLOCK_ID",nullable = false,updatable = false,insertable = false)
    BlockWaterBalanceSetting blockWaterBalanceSetting;


    @Column(name = "GEOM")
    Polygon geom;





    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geom) {
        this.geom = geom;
    }


    public BlockDetail() {
        super();
    }

    public void setBlockTagSetting(BlockTagSetting blockTagSetting) {
        this.blockTagSetting = blockTagSetting;
    }

    public BlockTagSetting getBlockTagSetting() {
        return blockTagSetting;
    }

    public BlockNightSetting getBlockNightSetting() {
        return blockNightSetting;
    }

    public void setBlockNightSetting(BlockNightSetting blockNightSetting) {
        this.blockNightSetting = blockNightSetting;
    }

    public BlockWaterBalanceSetting getBlockWaterBalanceSetting() {
        return blockWaterBalanceSetting;
    }

    public void setBlockWaterBalanceSetting(BlockWaterBalanceSetting blockWaterBalanceSetting) {
        this.blockWaterBalanceSetting = blockWaterBalanceSetting;
    }

    /*
    @Column(name = "INTEG_FLOW_TAG_NM",table = "WTWS_BLOCK_TAG")
    String integ_flow_tag_nm;

    @Column(name = "FLOW_TAG_NM",table = "WTWS_BLOCK_TAG")
    String flow_tag_nm;

    @Column(name = "PRESS_TAG_NM",table = "WTWS_BLOCK_TAG")
    String press_tag_nm;



    @Column(name = "FLOW_H_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal flow_h_va;
    @Column(name = "FLOW_HH_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal flow_hh_va;
    @Column(name = "FLOW_L_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal flow_l_va;
    @Column(name = "FLOW_LL_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal flow_ll_va;
    @Column(name = "FLOW_IS_HL_ENABLE",table = "WTWS_BLOCK_TAG")
    Boolean flow_is_hl_enable;


    @Column(name = "PRESS_H_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal press_h_va;
    @Column(name = "PRESS_HH_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal press_hh_va;
    @Column(name = "PRESS_L_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal press_l_va;
    @Column(name = "PRESS_LL_VA",table = "WTWS_BLOCK_TAG")
    BigDecimal press_ll_va;
    @Column(name = "PRESS_IS_HL_ENABLE",table = "WTWS_BLOCK_TAG")
    Boolean press_is_hl_enable;


    @Column(name = "NIGHT_START_HH",table = "WTWS_BLOCK_NIGHT_SETTING")
    Integer night_start_hh;
    @Column(name = "NIGHT_START_MI",table = "WTWS_BLOCK_NIGHT_SETTING")
    Integer night_start_mi;
    @Column(name = "NIGHT_END_HH",table = "WTWS_BLOCK_NIGHT_SETTING")
    Integer night_end_hh;
    @Column(name = "NIGHT_END_MI",table = "WTWS_BLOCK_NIGHT_SETTING")
    Integer night_end_mi;
    @Column(name = "NIGHT_MIN_FLOW_VA",table = "WTWS_BLOCK_NIGHT_SETTING")
    BigDecimal night_min_flow_va;







    public String getInteg_flow_tag_nm() {
        return integ_flow_tag_nm;
    }

    public void setInteg_flow_tag_nm(String integ_flow_tag_nm) {
        this.integ_flow_tag_nm = integ_flow_tag_nm;
    }

    public String getFlow_tag_nm() {
        return flow_tag_nm;
    }

    public void setFlow_tag_nm(String flow_tag_nm) {
        this.flow_tag_nm = flow_tag_nm;
    }

    public String getPress_tag_nm() {
        return press_tag_nm;
    }

    public void setPress_tag_nm(String press_tag_nm) {
        this.press_tag_nm = press_tag_nm;
    }


    public BigDecimal getFlow_h_va() {
        return flow_h_va;
    }

    public void setFlow_h_va(BigDecimal flow_h_va) {
        this.flow_h_va = flow_h_va;
    }

    public BigDecimal getFlow_hh_va() {
        return flow_hh_va;
    }

    public void setFlow_hh_va(BigDecimal flow_hh_va) {
        this.flow_hh_va = flow_hh_va;
    }

    public BigDecimal getFlow_l_va() {
        return flow_l_va;
    }

    public void setFlow_l_va(BigDecimal flow_l_va) {
        this.flow_l_va = flow_l_va;
    }

    public BigDecimal getFlow_ll_va() {
        return flow_ll_va;
    }

    public void setFlow_ll_va(BigDecimal flow_ll_va) {
        this.flow_ll_va = flow_ll_va;
    }

    public boolean isFlow_is_hl_enable() {
        return flow_is_hl_enable;
    }

    public void setFlow_is_hl_enable(boolean flow_is_hl_enable) {
        this.flow_is_hl_enable = flow_is_hl_enable;
    }

    public BigDecimal getPress_h_va() {
        return press_h_va;
    }

    public void setPress_h_va(BigDecimal press_h_va) {
        this.press_h_va = press_h_va;
    }

    public BigDecimal getPress_hh_va() {
        return press_hh_va;
    }

    public void setPress_hh_va(BigDecimal press_hh_va) {
        this.press_hh_va = press_hh_va;
    }

    public BigDecimal getPress_l_va() {
        return press_l_va;
    }

    public void setPress_l_va(BigDecimal press_l_va) {
        this.press_l_va = press_l_va;
    }

    public BigDecimal getPress_ll_va() {
        return press_ll_va;
    }

    public void setPress_ll_va(BigDecimal press_ll_va) {
        this.press_ll_va = press_ll_va;
    }

    public boolean isPress_is_hl_enable() {
        return press_is_hl_enable;
    }

    public void setPress_is_hl_enable(boolean press_is_hl_enable) {
        this.press_is_hl_enable = press_is_hl_enable;
    }

    public Boolean getPress_is_hl_enable() {
        return press_is_hl_enable;
    }

    public void setPress_is_hl_enable(Boolean press_is_hl_enable) {
        this.press_is_hl_enable = press_is_hl_enable;
    }

    public Boolean getFlow_is_hl_enable() {
        return flow_is_hl_enable;
    }

    public void setFlow_is_hl_enable(Boolean flow_is_hl_enable) {
        this.flow_is_hl_enable = flow_is_hl_enable;
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
    */
}
