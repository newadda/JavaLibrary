package org.onecell.example.jpa.entity.etc;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="WTWS_PHONE")
public class Phone {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="sms_seq")
    @TableGenerator(
            name="sms_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="sms_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID",columnDefinition="NUMBER(19)")
    Long id;

    @Column(name = "PHONE_NO")
    String phone_no;

    @Column(name = "NONCONFIRM_PERIOD_VA")
    Integer nonconfirm_period_va=0;

    @Column(name = "PERIOD_VA")
    Integer period_va=10;

    @Column(name = "LAST_SENT_DT")
    LocalDateTime last_sent_dt=LocalDateTime.now();

    @Column(name = "IS_ENABLE")
    Boolean is_enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public Integer getNonconfirm_period_va() {
        return nonconfirm_period_va;
    }

    public void setNonconfirm_period_va(Integer nonconfirm_period_va) {
        this.nonconfirm_period_va = nonconfirm_period_va;
    }

    public Integer getPeriod_va() {
        return period_va;
    }

    public void setPeriod_va(Integer period_va) {
        this.period_va = period_va;
    }

    public LocalDateTime getLast_sent_dt() {
        return last_sent_dt;
    }

    public void setLast_sent_dt(LocalDateTime last_sent_dt) {
        this.last_sent_dt = last_sent_dt;
    }

    public Boolean getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(Boolean is_enable) {
        this.is_enable = is_enable;
    }
}
