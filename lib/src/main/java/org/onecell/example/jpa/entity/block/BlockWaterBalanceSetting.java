package org.onecell.example.jpa.entity.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="WTWS_BLOCK_WATER_BALANCE_SETTING")
public class BlockWaterBalanceSetting {
    @Id
    @Column(name = "BLOCK_ID",columnDefinition="NUMBER(19)")
    Long block_id;

    @Column(name = "BILLED_UNMETERED_RT")
    BigDecimal billed_unmetered_rt=new BigDecimal(0);

    @Column(name = "LENT_RT")
    BigDecimal lent_rt=new BigDecimal(0);

    @Column(name = "ETC_IMPOSE_RT")
    BigDecimal etc_impose_rt=new BigDecimal(0);

    @Column(name = "INSENS_RT")
    BigDecimal insens_rt=new BigDecimal(0);

    @Column(name = "BUSINESS_RT")
    BigDecimal business_rt=new BigDecimal(0);

    @Column(name = "PUBLIC_RT")
    BigDecimal public_rt=new BigDecimal(0);

    @Column(name = "ILLEGAL_RT")
    BigDecimal illegal_rt=new BigDecimal(0);

    @Column(name = "DEDUCED_RT")
    BigDecimal deduced_rt=new BigDecimal(0);

    @Column(name = "LEAK_RT")
    BigDecimal leak_rt=new BigDecimal(1);

    @Column(name = "SUPPLY_DD")
    int supply_dd=15;


    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
    }

    public BigDecimal getBilled_unmetered_rt() {
        return billed_unmetered_rt;
    }

    public void setBilled_unmetered_rt(BigDecimal billed_unmetered_rt) {
        this.billed_unmetered_rt = billed_unmetered_rt;
    }

    public BigDecimal getLent_rt() {
        return lent_rt;
    }

    public void setLent_rt(BigDecimal lent_rt) {
        this.lent_rt = lent_rt;
    }

    public BigDecimal getEtc_impose_rt() {
        return etc_impose_rt;
    }

    public void setEtc_impose_rt(BigDecimal etc_impose_rt) {
        this.etc_impose_rt = etc_impose_rt;
    }

    public BigDecimal getInsens_rt() {
        return insens_rt;
    }

    public void setInsens_rt(BigDecimal insens_rt) {
        this.insens_rt = insens_rt;
    }

    public BigDecimal getBusiness_rt() {
        return business_rt;
    }

    public void setBusiness_rt(BigDecimal business_rt) {
        this.business_rt = business_rt;
    }

    public BigDecimal getPublic_rt() {
        return public_rt;
    }

    public void setPublic_rt(BigDecimal public_rt) {
        this.public_rt = public_rt;
    }

    public BigDecimal getIllegal_rt() {
        return illegal_rt;
    }

    public void setIllegal_rt(BigDecimal illegal_rt) {
        this.illegal_rt = illegal_rt;
    }

    public BigDecimal getDeduced_rt() {
        return deduced_rt;
    }

    public void setDeduced_rt(BigDecimal deduced_rt) {
        this.deduced_rt = deduced_rt;
    }

    public BigDecimal getLeak_rt() {
        return leak_rt;
    }

    public void setLeak_rt(BigDecimal leak_rt) {
        this.leak_rt = leak_rt;
    }

    public int getSupply_dd() {
        return supply_dd;
    }

    public void setSupply_dd(int supply_dd) {
        this.supply_dd = supply_dd;
    }
}
