package org.onecell.example.jpa.entity.block.log;


import org.hibernate.annotations.Nationalized;
import org.waterworks.lib.db.code.impl.DataType;
import org.waterworks.lib.db.code.impl.OccurType;
import org.waterworks.lib.db.entity.block.Block;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="WTWS_BLOCK_ABNORMAL_LOG")
public class BlockAbnormalLog {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="block_abnormal_log_seq")
    @TableGenerator(
            name="block_abnormal_log_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_abnormal_log_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    Long id;


    @Column(name = "BLOCK_ID")
    Long block_id;


    @Column(name = "OCCUR_DT")
    LocalDateTime occur_dt;

    @Enumerated(EnumType.STRING)
    @Column(name = "DATA_TY")
    DataType data_ty;

    @Enumerated(EnumType.STRING)
    @Column(name = "OCCUR_TY")
    OccurType occur_ty;

    @Nationalized
    @Column(name = "MESG_DC")
    String mesg_dc;

    @OneToOne
    @JoinColumn(name = "BLOCK_ID",referencedColumnName = "ID",insertable = false,updatable = false)
    Block block;


    public Block getBlock() {
        return block;
    }

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

    public LocalDateTime getOccur_dt() {
        return occur_dt;
    }

    public void setOccur_dt(LocalDateTime occur_dt) {
        this.occur_dt = occur_dt;
    }

    public DataType getData_ty() {
        return data_ty;
    }

    public void setData_ty(DataType data_ty) {
        this.data_ty = data_ty;
    }

    public OccurType getOccur_ty() {
        return occur_ty;
    }

    public void setOccur_ty(OccurType occur_ty) {
        this.occur_ty = occur_ty;
    }

    public String getMesg_dc() {
        return mesg_dc;
    }

    public void setMesg_dc(String mesg_dc) {
        this.mesg_dc = mesg_dc;
    }
}
