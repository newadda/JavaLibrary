package org.onecell.example.jpa.entity.block;


import org.waterworks.lib.db.entity.tag.Tag;

import javax.persistence.*;

@Entity
@Table(name="WTWS_BLOCK_CONSUME_TAG_SETTING")
public class BlockConsumeTagSetting {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="block_consume_tag_setting_seq")
    @TableGenerator(
            name="block_consume_tag_setting_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_consume_tag_setting_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    Long id;

    @Column(name = "BLOCK_ID")
    Long block_id;

    @Column(name = "TAG_ID")
    Long tag_id;


    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name="TAG_ID",referencedColumnName="ID" ,nullable=false,updatable = false,insertable = false)
    Tag tag_info;

    public Tag getTag_info() {
        return tag_info;
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

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }
}
