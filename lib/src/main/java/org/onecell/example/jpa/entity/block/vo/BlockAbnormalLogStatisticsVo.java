package org.onecell.example.jpa.entity.block.vo;

public class BlockAbnormalLogStatisticsVo {

    String data_ty; // 종류 예) 유량 수압
    String occur_ty; // 발생이유 예)
    Long count; //횟수

    public BlockAbnormalLogStatisticsVo(String data_ty, String occur_ty, Long count) {
        this.data_ty = data_ty;
        this.occur_ty = occur_ty;
        this.count = count;
    }

    public String getData_ty() {
        return data_ty;
    }

    public String getOccur_ty() {
        return occur_ty;
    }

    public Long getCount() {
        return count;
    }
}
