package org.onecell.example.jpa.entity.block.analysis;

public class BlockAnomalyPressVo {
    Long block_id;
    String tag_name=null;
    Double avg;
    Double stddev;
    Double high;
    Double low;
    Long high_over_count;
    Long low_under_count;
    Long total_count;

    public BlockAnomalyPressVo(Long block_id, String tag_name, Double avg, Double stddev, Double high, Double low, Long high_over_count, Long low_under_count, Long total_count) {
        this.block_id = block_id;
        this.tag_name = tag_name;
        this.avg = avg;
        this.stddev = stddev;
        this.high = high;
        this.low = low;
        this.high_over_count = high_over_count;
        this.low_under_count = low_under_count;
        this.total_count = total_count;
    }

    public Long getBlock_id() {
        return block_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public Double getAvg() {
        return avg;
    }

    public Double getStddev() {
        return stddev;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Long getHigh_over_count() {
        return high_over_count;
    }

    public Long getLow_under_count() {
        return low_under_count;
    }

    public Long getTotal_count() {
        return total_count;
    }
}
