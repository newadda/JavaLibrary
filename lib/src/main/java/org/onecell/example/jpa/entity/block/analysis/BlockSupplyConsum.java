package org.onecell.example.jpa.entity.block.analysis;

import com.querydsl.core.annotations.QueryProjection;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.waterworks.lib.db.entity.block.Block;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="WTWS_BLOCK_SUPPLY_CONSUM")
public class BlockSupplyConsum {

    public BlockSupplyConsum() {
    }

    @QueryProjection
    public BlockSupplyConsum(Long id, Long block_id, LocalDateTime analysis_dt, LocalDateTime saved_dt, BigDecimal supply_va, Integer supply_corr_co, Integer supply_norm_co, Integer supply_loss_co, BigDecimal consum_va, Integer csmr_calcu_co, Integer csmr_total_co) {
        this.id = id;
        this.block_id = block_id;
        this.analysis_dt = analysis_dt;
        this.saved_dt = saved_dt;
        this.supply_va = supply_va;
        this.supply_corr_co = supply_corr_co;
        this.supply_norm_co = supply_norm_co;
        this.supply_loss_co = supply_loss_co;
        this.consum_va = consum_va;
        this.csmr_calcu_co = csmr_calcu_co;
        this.csmr_total_co = csmr_total_co;
    }

    @QueryProjection
    public BlockSupplyConsum( Long block_id, Integer analysis_year,Integer analysis_month,Integer analysis_day,  LocalDateTime saved_dt, BigDecimal supply_va, Integer supply_corr_co, Integer supply_norm_co, Integer supply_loss_co, BigDecimal consum_va, Integer csmr_calcu_co, Integer csmr_total_co) {
         this.block_id = block_id;
        this.analysis_dt = LocalDateTime.of(analysis_year,analysis_month,analysis_day,0,0,0);
        this.saved_dt = saved_dt;
        this.supply_va = supply_va;
        this.supply_corr_co = supply_corr_co;
        this.supply_norm_co = supply_norm_co;
        this.supply_loss_co = supply_loss_co;
        this.consum_va = consum_va;
        this.csmr_calcu_co = csmr_calcu_co;
        this.csmr_total_co = csmr_total_co;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="block_supply_consum_seq")
    @TableGenerator(
            name="block_supply_consum_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_supply_consum_seq",allocationSize = 1,initialValue = 1
    )




    @Column(name = "ID")
    Long id;

    @Column(name = "BLOCK_ID")
    Long block_id;


    @Column(name = "ANALYSIS_DT")
    LocalDateTime analysis_dt;



    @Column(name = "SAVED_DT")
    LocalDateTime saved_dt=LocalDateTime.now();

    @Column(name = "SUPPLY_VA")
    BigDecimal supply_va;

    @Column(name = "SUPPLY_CORR_CO")
    Integer supply_corr_co=0; // 보정 개수

    @Column(name = "SUPPLY_NORM_CO")
    Integer supply_norm_co=0; // 정상 개수

    @Column(name = "SUPPLY_LOSS_CO")
    Integer supply_loss_co=0; // 손실 개수

    @Column(name = "CONSUM_VA")
    BigDecimal consum_va=new BigDecimal(0);

    @Column(name = "CSMR_CALCU_CO")
    Integer csmr_calcu_co=0; // 실제로 계산에 사용된 수용가 데이터

    @Column(name = "CSMR_TOTAL_CO")
    Integer csmr_total_co=0; // 전체 있어야 하는 총 수용가 데이터

    @Fetch(FetchMode.JOIN)
    @OneToOne(optional = false)
    @JoinColumn(name = "block_id",referencedColumnName = "id",updatable = false,insertable = false,nullable = false)
    Block block;



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

    public LocalDateTime getAnalysis_dt() {
        return analysis_dt;
    }

    public void setAnalysis_dt(LocalDateTime analysis_dt) {
        this.analysis_dt = analysis_dt;
    }

    public LocalDateTime getSaved_dt() {
        return saved_dt;
    }

    public void setSaved_dt(LocalDateTime saved_dt) {
        this.saved_dt = saved_dt;
    }

    public BigDecimal getSupply_va() {
        return supply_va;
    }

    public void setSupply_va(BigDecimal supply_va) {
        this.supply_va = supply_va;
    }

    public Integer getSupply_corr_co() {
        return supply_corr_co;
    }

    public void setSupply_corr_co(Integer supply_corr_co) {
        this.supply_corr_co = supply_corr_co;
    }

    public Integer getSupply_norm_co() {
        return supply_norm_co;
    }

    public void setSupply_norm_co(Integer supply_norm_co) {
        this.supply_norm_co = supply_norm_co;
    }

    public Integer getSupply_loss_co() {
        return supply_loss_co;
    }

    public void setSupply_loss_co(Integer supply_loss_co) {
        this.supply_loss_co = supply_loss_co;
    }

    public BigDecimal getConsum_va() {
        return consum_va;
    }

    public void setConsum_va(BigDecimal consum_va) {
        this.consum_va = consum_va;
    }

    public Integer getCsmr_calcu_co() {
        return csmr_calcu_co;
    }

    public void setCsmr_calcu_co(Integer csmr_calcu_co) {
        this.csmr_calcu_co = csmr_calcu_co;
    }

    public Integer getCsmr_total_co() {
        return csmr_total_co;
    }

    public void setCsmr_total_co(Integer csmr_total_co) {
        this.csmr_total_co = csmr_total_co;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
