package org.onecell.example.jpa.entity.block.analysis;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="WTWS_BLOCK_WATER_BALANCE")
public class BlockWaterBalance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="block_water_balance_seq")
    @TableGenerator(
            name="block_water_balance_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_water_balance_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    Long id;


    @Column(name = "BLOCK_ID",nullable = false)
    Long block_id;

    @Column(name = "ANALYSIS_YY",nullable = false)
    Integer analysis_yy;

    @Column(name = "ANALYSIS_MM",nullable = false)
    Integer analysis_mm;



    @Column(name = "BILLED_METERED_VA",nullable = false)
    BigDecimal billed_metered_va=new BigDecimal(0);

    @Column(name = "BILLED_UNMETERED_VA",nullable = false)
    BigDecimal billed_unmetered_va=new BigDecimal(0);

    @Column(name = "LENT_VA",nullable = false)
    BigDecimal lent_va=new BigDecimal(0);

    @Column(name = "ETC_IMPOSE_VA",nullable = false)
    BigDecimal etc_impose_va=new BigDecimal(0);

    @Column(name = "INSENS_VA",nullable = false)
    BigDecimal insens_va=new BigDecimal(0);

    @Column(name = "BUSINESS_VA",nullable = false)
    BigDecimal business_va=new BigDecimal(0);

    @Column(name = "PUBLIC_VA",nullable = false)
    BigDecimal public_va=new BigDecimal(0);

    @Column(name = "ILLEGAL_VA",nullable = false)
    BigDecimal illegal_va=new BigDecimal(0);

    @Column(name = "DEDUCED_VA",nullable = false)
    BigDecimal deduced_va=new BigDecimal(0);

    @Column(name = "LEAK_VA",nullable = false)
    BigDecimal leak_va=new BigDecimal(0);

    @Column(name = "SUPPLY_VA",nullable = false)
    BigDecimal supply_va=new BigDecimal(0);

    @Column(name = "SUPPLY_START_DT",nullable = false)
    LocalDateTime supply_start_dt;

    @Column(name = "SUPPLY_END_DT",nullable = false)
    LocalDateTime supply_end_dt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
    }

    public Integer getAnalysis_yy() {
        return analysis_yy;
    }

    public void setAnalysis_yy(Integer analysis_yy) {
        this.analysis_yy = analysis_yy;
    }

    public Integer getAnalysis_mm() {
        return analysis_mm;
    }

    public void setAnalysis_mm(Integer analysis_mm) {
        this.analysis_mm = analysis_mm;
    }

    public BigDecimal getBilled_metered_va() {
        return billed_metered_va;
    }

    public void setBilled_metered_va(BigDecimal billed_metered_va) {
        this.billed_metered_va = billed_metered_va;
    }

    public BigDecimal getBilled_unmetered_va() {
        return billed_unmetered_va;
    }

    public void setBilled_unmetered_va(BigDecimal billed_unmetered_va) {
        this.billed_unmetered_va = billed_unmetered_va;
    }

    public BigDecimal getLent_va() {
        return lent_va;
    }

    public void setLent_va(BigDecimal lent_va) {
        this.lent_va = lent_va;
    }

    public BigDecimal getEtc_impose_va() {
        return etc_impose_va;
    }

    public void setEtc_impose_va(BigDecimal etc_impose_va) {
        this.etc_impose_va = etc_impose_va;
    }

    public BigDecimal getInsens_va() {
        return insens_va;
    }

    public void setInsens_va(BigDecimal insens_va) {
        this.insens_va = insens_va;
    }

    public BigDecimal getBusiness_va() {
        return business_va;
    }

    public void setBusiness_va(BigDecimal business_va) {
        this.business_va = business_va;
    }

    public BigDecimal getPublic_va() {
        return public_va;
    }

    public void setPublic_va(BigDecimal public_va) {
        this.public_va = public_va;
    }

    public BigDecimal getIllegal_va() {
        return illegal_va;
    }

    public void setIllegal_va(BigDecimal illegal_va) {
        this.illegal_va = illegal_va;
    }

    public BigDecimal getDeduced_va() {
        return deduced_va;
    }

    public void setDeduced_va(BigDecimal deduced_va) {
        this.deduced_va = deduced_va;
    }

    public BigDecimal getLeak_va() {
        return leak_va;
    }

    public void setLeak_va(BigDecimal leak_va) {
        this.leak_va = leak_va;
    }

    public BigDecimal getSupply_va() {
        return supply_va;
    }

    public void setSupply_va(BigDecimal supply_va) {
        this.supply_va = supply_va;
    }

    public LocalDateTime getSupply_start_dt() {
        return supply_start_dt;
    }

    public void setSupply_start_dt(LocalDateTime supply_start_dt) {
        this.supply_start_dt = supply_start_dt;
    }

    public LocalDateTime getSupply_end_dt() {
        return supply_end_dt;
    }

    public void setSupply_end_dt(LocalDateTime supply_end_dt) {
        this.supply_end_dt = supply_end_dt;
    }
}
