package org.onecell.example.jpa.entity.block.vo;

public class AbnormalLogStatisticsByBlockVo {
    Long block_id;
    String data_ty;
    Long count;

    public AbnormalLogStatisticsByBlockVo(Long block_id, String data_ty, Long count) {
        this.block_id = block_id;
        this.data_ty = data_ty;
        this.count = count;
    }

    public Long getBlock_id() {
        return block_id;
    }

    public String getData_ty() {
        return data_ty;
    }

    public Long getCount() {
        return count;
    }
}
