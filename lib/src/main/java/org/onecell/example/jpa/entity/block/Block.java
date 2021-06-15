package org.onecell.example.jpa.entity.block;


import org.geolatte.geom.Polygon;
import org.hibernate.annotations.Nationalized;
import org.lib.misc.geo.GeometryGeolatteUtil;
import org.waterworks.lib.db.code.impl.BlockType;
import org.waterworks.lib.db.converter.impl.BlockTypeToStringConverter;

import javax.persistence.*;


@Entity
@Table(name="WTWS_BLOCK")
public class Block extends ABlock {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="block_seq")
    @TableGenerator(
            name="block_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="block_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    Long id;

    @Column(name = "PARENT_ID")
    Long parent_id;

    @Nationalized
    @Column(name = "BK_NM")
    String name;

    //@Column(name = "LFT")
    @Transient
    int left;

    //@Column(name = "RGT")
    @Transient
    int right;

    @Column(name = "PRIORITY")
    Long priority;


    @Column(name = "BK_CL",columnDefinition="CHAR(6)")
    @Convert(converter = BlockTypeToStringConverter.class)
    BlockType blocktype;

    @Column(name = "GEOM")
    Polygon geom;





    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geom) {
        this.geom = geom;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        // 부모 식별자가 자기자신이면 안된다.
        if((this.id!=null) && this.id == parent_id)
        {
            return;
        }

        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public BlockType getBlocktype() {
        return blocktype;
    }

    public void setBlocktype(BlockType blocktype) {
        this.blocktype = blocktype;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    //@Basic(fetch=FetchType.EAGER)
    //@Formula("(SELECT (COUNT(parent.id) - 1) FROM WTWS_BLOCK  node, WTWS_BLOCK  parent WHERE node.lft BETWEEN parent.lft AND parent.rgt AND node.id=ID GROUP BY node.id,node.lft )")
    //Long depth;


    public Block()  {
       geom = GeometryGeolatteUtil.defaultPolygon();
    }


}
