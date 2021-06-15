package org.onecell.example.jpa.entity.block.analysis;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="WTWS_BLOCK_MIN_FLOW")
public class BlockMinFlow {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="block_min_flow_seq")
    @TableGenerator(
            name="block_min_flow_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_min_flow_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    Long id;

    @Column(name = "BLOCK_ID",nullable = false)
    Long block_id;

    @Column(name = "ANALYSIS_DE",nullable = false)
    LocalDate analysis_de;

    @Column(name = "START_DT",nullable = false)
    LocalDateTime start_dt;

    @Column(name = "END_DT",nullable = false)
    LocalDateTime end_dt;

    @Column(name = "MIN_DT")
    LocalDateTime min_dt;

    @Column(name = "MIN_VA")
    BigDecimal min_va;

    @Column(name = "SAVED_DT")
    LocalDateTime saved_dt;


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

    public LocalDate getAnalysis_de() {
        return analysis_de;
    }

    public void setAnalysis_de(LocalDate analysis_de) {
        this.analysis_de = analysis_de;
    }

    public LocalDateTime getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(LocalDateTime start_dt) {
        this.start_dt = start_dt;
    }

    public LocalDateTime getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(LocalDateTime end_dt) {
        this.end_dt = end_dt;
    }

    public LocalDateTime getMin_dt() {
        return min_dt;
    }

    public void setMin_dt(LocalDateTime min_dt) {
        this.min_dt = min_dt;
    }

    public BigDecimal getMin_va() {
        return min_va;
    }

    public void setMin_va(BigDecimal min_va) {
        this.min_va = min_va;
    }

    public LocalDateTime getSaved_dt() {
        return saved_dt;
    }

    public void setSaved_dt(LocalDateTime saved_dt) {
        this.saved_dt = saved_dt;
    }
}
