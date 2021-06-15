package org.onecell.example.jpa.entity.block.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BlockLeakAnalysisVo {

    public static class PressDay
    {
        BigDecimal  min_value;
        BigDecimal max_value;
        Double avg_value;

        public BigDecimal getMin_value() {
            return min_value;
        }

        public void setMin_value(BigDecimal min_value) {
            this.min_value = min_value;
        }

        public BigDecimal getMax_value() {
            return max_value;
        }

        public void setMax_value(BigDecimal max_value) {
            this.max_value = max_value;
        }

        public Double getAvg_value() {
            return avg_value;
        }

        public void setAvg_value(Double avg_value) {
            this.avg_value = avg_value;
        }
    }


    public static class FlowDay
    {
        private LocalDateTime min_datetime;//최소유량시 시간
        private BigDecimal min_setting_value;//최소유량 설정값
        private BigDecimal min_value;//단위 m^3 최소유량값

        public LocalDateTime getMin_datetime() {
            return min_datetime;
        }

        public void setMin_datetime(LocalDateTime min_datetime) {
            this.min_datetime = min_datetime;
        }

        public BigDecimal getMin_value() {
            return min_value;
        }

        public void setMin_value(BigDecimal min_value) {
            this.min_value = min_value;
        }

        public BigDecimal getMin_setting_value() {
            return min_setting_value;
        }

        public void setMin_setting_value(BigDecimal min_setting_value) {
            this.min_setting_value = min_setting_value;
        }
    }

    public static class FlowMonth
    {

        private BigDecimal min_setting_value;//최소유량 설정값
        private BigDecimal min_value;//단위 m^3 최소유량값

        public BigDecimal getMin_setting_value() {
            return min_setting_value;
        }

        public void setMin_setting_value(BigDecimal min_setting_value) {
            this.min_setting_value = min_setting_value;
        }

        public BigDecimal getMin_value() {
            return min_value;
        }

        public void setMin_value(BigDecimal min_value) {
            this.min_value = min_value;
        }
    }

    public static class MinSupply
    {
        LocalDateTime atmin_datetime; //최소공급량일때 시간
        BigDecimal atmin_supply_value;//단위 m^3/h  //최소공급량
        BigDecimal atmin_consume_value;//단위 m^3/h //최소공급량일 사용량
        BigDecimal atmin_bleak_per_hour;//단위 m^3/h 최소공급량일때 시간 배경누수량
        BigDecimal atmin_calculated_bleak_per_day;//단위 m^3/day 계산된 일별 배경누수량

        BigDecimal real_bleak_per_day; //단위 m^3/day 해당 일자 실제 배경누수량
        BigDecimal real_supply_per_day; //단위 m^3/day 해당 일자 실제 공급량
        BigDecimal real_consume_per_day; //단위 m^3/day 해당 일자 실제 사용량
        BigDecimal real_flow_rate; //단위 m^3/day 해당 실제 유수율

        public LocalDateTime getAtmin_datetime() {
            return atmin_datetime;
        }

        public void setAtmin_datetime(LocalDateTime atmin_datetime) {
            this.atmin_datetime = atmin_datetime;
        }

        public BigDecimal getAtmin_supply_value() {
            return atmin_supply_value;
        }

        public void setAtmin_supply_value(BigDecimal atmin_supply_value) {
            this.atmin_supply_value = atmin_supply_value;
        }

        public BigDecimal getAtmin_consume_value() {
            return atmin_consume_value;
        }

        public void setAtmin_consume_value(BigDecimal atmin_consume_value) {
            this.atmin_consume_value = atmin_consume_value;
        }

        public BigDecimal getAtmin_bleak_per_hour() {
            return atmin_bleak_per_hour;
        }

        public void setAtmin_bleak_per_hour(BigDecimal atmin_bleak_per_hour) {
            this.atmin_bleak_per_hour = atmin_bleak_per_hour;
        }

        public BigDecimal getAtmin_calculated_bleak_per_day() {
            return atmin_calculated_bleak_per_day;
        }

        public void setAtmin_calculated_bleak_per_day(BigDecimal atmin_calculated_bleak_per_day) {
            this.atmin_calculated_bleak_per_day = atmin_calculated_bleak_per_day;
        }

        public BigDecimal getReal_bleak_per_day() {
            return real_bleak_per_day;
        }

        public void setReal_bleak_per_day(BigDecimal real_bleak_per_day) {
            this.real_bleak_per_day = real_bleak_per_day;
        }

        public BigDecimal getReal_supply_per_day() {
            return real_supply_per_day;
        }

        public void setReal_supply_per_day(BigDecimal real_supply_per_day) {
            this.real_supply_per_day = real_supply_per_day;
        }

        public BigDecimal getReal_consume_per_day() {
            return real_consume_per_day;
        }

        public void setReal_consume_per_day(BigDecimal real_consume_per_day) {
            this.real_consume_per_day = real_consume_per_day;
        }

        public BigDecimal getReal_flow_rate() {
            return real_flow_rate;
        }

        public void setReal_flow_rate(BigDecimal real_flow_rate) {
            this.real_flow_rate = real_flow_rate;
        }
    }

    /// 중앙값
    public static class MedianRate{
        BigDecimal median_rate;
        Long median_rate_lt_count;

        public BigDecimal getMedian_rate() {
            return median_rate;
        }

        public void setMedian_rate(BigDecimal median_rate) {
            this.median_rate = median_rate;
        }

        public Long getMedian_rate_lt_count() {
            return median_rate_lt_count;
        }

        public void setMedian_rate_lt_count(Long median_rate_lt_count) {
            this.median_rate_lt_count = median_rate_lt_count;
        }
    }

    public static class LinearRegression
    {
        Double slope; // 기울기
        Double intercept; // Y 절편
        Double angle; // 각도



        public Double getSlope() {
            return slope;
        }

        public void setSlope(Double slope) {
            this.slope = slope;
            if(slope!=null && !Double.isNaN(slope))
            {

                BigDecimal multiply = BigDecimal.valueOf(180.0).divide(BigDecimal.valueOf(Math.PI), 5, RoundingMode.FLOOR)
                        .multiply(BigDecimal.valueOf(Math.atan(slope)));
                angle = multiply.setScale(8,RoundingMode.FLOOR).doubleValue();

            }
        }

        public Double getAngle() {
            return angle;
        }

        public Double getIntercept() {
            return intercept;
        }

        public void setIntercept(Double intercept) {
            this.intercept = intercept;
        }
    }

   public static class etc
    {
        Boolean is_min_flow_over;  // 설정최소유량 초과여부(하루내);
        Boolean is_min_flow_over_in_month; // 1개월 내 설정최소유량 초과여부
        Integer recent_bleak_over_count; // 최소시간 배경누수량 수렴강도
        Integer recent_flow_rate_under_count; // 유수율 하락 후 미복귀 강도

        public Boolean getIs_min_flow_over() {
            return is_min_flow_over;
        }

        public void setIs_min_flow_over(Boolean is_min_flow_over) {
            this.is_min_flow_over = is_min_flow_over;
        }

        public Boolean getIs_min_flow_over_in_month() {
            return is_min_flow_over_in_month;
        }

        public void setIs_min_flow_over_in_month(Boolean is_min_flow_over_in_month) {
            this.is_min_flow_over_in_month = is_min_flow_over_in_month;
        }

        public Integer getRecent_bleak_over_count() {
            return recent_bleak_over_count;
        }

        public void setRecent_bleak_over_count(Integer recent_bleak_over_count) {
            this.recent_bleak_over_count = recent_bleak_over_count;
        }

        public Integer getRecent_flow_rate_under_count() {
            return recent_flow_rate_under_count;
        }

        public void setRecent_flow_rate_under_count(Integer recent_flow_rate_under_count) {
            this.recent_flow_rate_under_count = recent_flow_rate_under_count;
        }
    }

    Long block_id;

    LocalDate date;

    PressDay press_day_analysis = new PressDay();
    PressDay press_night_day_analysis = new PressDay();
    FlowDay flow_day_analysis = new FlowDay();
    FlowMonth flow_month_analysis = new FlowMonth();
    MinSupply supply_analysis = new MinSupply();
    MedianRate median_rate= new MedianRate();
    LinearRegression linear_regression =new LinearRegression();


    etc etc_analysis = new etc();


    public BlockLeakAnalysisVo(Long block_id, LocalDate date) {
        this.block_id = block_id;
        this.date = date;
    }

    public Long getBlock_id() {
        return block_id;
    }

    public void setBlock_id(Long block_id) {
        this.block_id = block_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public FlowDay getFlow_day_analysis() {
        return flow_day_analysis;
    }

    public void setFlow_day_analysis(FlowDay flow_day_analysis) {
        this.flow_day_analysis = flow_day_analysis;
    }

    public MinSupply getSupply_analysis() {
        return supply_analysis;
    }

    public void setSupply_analysis(MinSupply supply_analysis) {
        this.supply_analysis = supply_analysis;
    }

    public etc getEtc_analysis() {
        return etc_analysis;
    }

    public void setEtc_analysis(etc etc_analysis) {
        this.etc_analysis = etc_analysis;
    }

    public FlowMonth getFlow_month_analysis() {
        return flow_month_analysis;
    }

    public void setFlow_month_analysis(FlowMonth flow_month_analysis) {
        this.flow_month_analysis = flow_month_analysis;
    }

    public MedianRate getMedian_rate() {
        return median_rate;
    }

    public LinearRegression getLinear_regression() {
        return linear_regression;
    }

    public PressDay getPress_day_analysis() {
        return press_day_analysis;
    }

    public PressDay getPress_night_day_analysis() {
        return press_night_day_analysis;
    }
}
