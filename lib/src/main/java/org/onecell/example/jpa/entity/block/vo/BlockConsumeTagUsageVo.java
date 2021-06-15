package org.onecell.example.jpa.entity.block.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class BlockConsumeTagUsageVo {
    public static class Item{
        LocalDateTime datetime;
        BigDecimal total;

        public Item(LocalDateTime datetime, BigDecimal total) {
            this.datetime = datetime;
            this.total = total;
        }

        public LocalDateTime getDatetime() {
            return datetime;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }
    Long block_id;
    List<Item> datas = new LinkedList<>();

    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
    }

    public List<Item> getDatas() {
        return datas;
    }
}
