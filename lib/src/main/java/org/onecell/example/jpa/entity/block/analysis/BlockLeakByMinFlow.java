package org.onecell.example.jpa.entity.block.analysis;

import org.waterworks.lib.db.code.impl.LeakType;
import org.waterworks.lib.db.converter.impl.LeakTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="WTWS_BLOCK_LEAK_BY_MIN_FLOW")
public class BlockLeakByMinFlow {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="block_leak_by_min_flow_seq")
    @TableGenerator(
            name="block_leak_by_min_flow_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_leak_by_min_flow_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    Long id;


    @Column(name = "BLOCK_ID",nullable = false)
    Long block_id;

    @Column(name = "ANALYSIS_DE",nullable = false)
    LocalDate analysis_de;


    @Column(name = "MIN_VA")
    BigDecimal min_va=null;

    @Column(name = "THRESHOLD_VA")
    BigDecimal threshold_va=null;

    @Convert(converter = LeakTypeConverter.class)
    @Column(name = "IS_LEAK")
    LeakType is_leak =LeakType.UNKNOWN;

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

    public BigDecimal getMin_va() {
        return min_va;
    }

    public void setMin_va(BigDecimal min_va) {
        this.min_va = min_va;
    }

    public BigDecimal getThreshold_va() {
        return threshold_va;
    }

    public void setThreshold_va(BigDecimal threshold_va) {
        this.threshold_va = threshold_va;
    }

    public LeakType getIs_leak() {
        return is_leak;
    }

    public void setIs_leak(LeakType is_leak) {
        this.is_leak = is_leak;
    }
}
