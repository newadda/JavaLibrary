package org.onecell.example.jpa.entity.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="WTWS_BLOCK_TAG_SETTING")
public class BlockTagSetting {

    @Id
    @Column(name = "BLOCK_ID",columnDefinition="NUMBER(19)")
    Long block_id;

    @Column(name = "INTEG_FLOW_TAG_NM")
    String integ_flow_tag_nm=null;

    @Column(name = "FLOW_TAG_NM")
    String flow_tag_nm=null;

    @Column(name = "PRESS_TAG_NM")
    String press_tag_nm=null;

    @Column(name = "FLOW_H_VA")
    BigDecimal flow_h_va=new BigDecimal(0);
    @Column(name = "FLOW_HH_VA")
    BigDecimal flow_hh_va=new BigDecimal(0);
    @Column(name = "FLOW_L_VA")
    BigDecimal flow_l_va=new BigDecimal(0);
    @Column(name = "FLOW_LL_VA")
    BigDecimal flow_ll_va=new BigDecimal(0);
    @Column(name = "FLOW_IS_HL_ENABLE")
    Boolean flow_is_hl_enable=false;


    @Column(name = "PRESS_H_VA")
    BigDecimal press_h_va=new BigDecimal(0);
    @Column(name = "PRESS_HH_VA")
    BigDecimal press_hh_va=new BigDecimal(0);
    @Column(name = "PRESS_L_VA")
    BigDecimal press_l_va=new BigDecimal(0);
    @Column(name = "PRESS_LL_VA")
    BigDecimal press_ll_va=new BigDecimal(0);
    @Column(name = "PRESS_IS_HL_ENABLE")
    Boolean press_is_hl_enable=false;

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

    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
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

    public Boolean getFlow_is_hl_enable() {
        return flow_is_hl_enable;
    }

    public void setFlow_is_hl_enable(Boolean flow_is_hl_enable) {
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

    public Boolean getPress_is_hl_enable() {
        return press_is_hl_enable;
    }

    public void setPress_is_hl_enable(Boolean press_is_hl_enable) {
        this.press_is_hl_enable = press_is_hl_enable;
    }
}
