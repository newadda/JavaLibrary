package org.onecell.example.jpa.entity.block.vo;

public class BlockFacilityInfoVo {
    Long block_id;

    // SA001 상수관로
    long sa001_count;
    Double sa001_length;

    // SA001_SAA005 급수관로로
    long sa001_saa005_count;
    Double sa001_saa005_length;

    // SA200 제수변
    long sa200_count;
    // SA201 역지변
    long sa201_count;
    // SA202 이토변
    long sa202_count;
    // SA203 배기변
    long sa203_count;
    // SA204 감압변
    long sa204_count;
    // SA205 안전변
    long sa205_count;

    // SA206 가압펌프장
    long sa206_count;

    // SA100 상수맨홀
    long sa100_count;

    // SA119  소화전
    long sa119_count;

    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
    }

    public long getSa001_count() {
        return sa001_count;
    }

    public void setSa001_count(long sa001_count) {
        this.sa001_count = sa001_count;
    }



    public long getSa001_saa005_count() {
        return sa001_saa005_count;
    }

    public void setSa001_saa005_count(long sa001_saa005_count) {
        this.sa001_saa005_count = sa001_saa005_count;
    }



    public long getSa200_count() {
        return sa200_count;
    }

    public void setSa200_count(long sa200_count) {
        this.sa200_count = sa200_count;
    }

    public long getSa201_count() {
        return sa201_count;
    }

    public void setSa201_count(long sa201_count) {
        this.sa201_count = sa201_count;
    }

    public long getSa202_count() {
        return sa202_count;
    }

    public void setSa202_count(long sa202_count) {
        this.sa202_count = sa202_count;
    }

    public long getSa203_count() {
        return sa203_count;
    }

    public void setSa203_count(long sa203_count) {
        this.sa203_count = sa203_count;
    }

    public long getSa204_count() {
        return sa204_count;
    }

    public void setSa204_count(long sa204_count) {
        this.sa204_count = sa204_count;
    }

    public long getSa205_count() {
        return sa205_count;
    }

    public void setSa205_count(long sa205_count) {
        this.sa205_count = sa205_count;
    }

    public long getSa206_count() {
        return sa206_count;
    }

    public void setSa206_count(long sa206_count) {
        this.sa206_count = sa206_count;
    }

    public long getSa100_count() {
        return sa100_count;
    }

    public void setSa100_count(long sa100_count) {
        this.sa100_count = sa100_count;
    }

    public long getSa119_count() {
        return sa119_count;
    }

    public void setSa119_count(long sa119_count) {
        this.sa119_count = sa119_count;
    }

    public Double getSa001_length() {
        return sa001_length;
    }

    public void setSa001_length(Double sa001_length) {
        this.sa001_length = sa001_length;
    }

    public Double getSa001_saa005_length() {
        return sa001_saa005_length;
    }

    public void setSa001_saa005_length(Double sa001_saa005_length) {
        this.sa001_saa005_length = sa001_saa005_length;
    }
}
