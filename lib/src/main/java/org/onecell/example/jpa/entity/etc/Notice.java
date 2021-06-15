package org.onecell.example.jpa.entity.etc;

import org.hibernate.annotations.Nationalized;
import org.waterworks.lib.db.code.impl.NoticeLevelType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="WTWS_NOTICE")
public class Notice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="notice_seq")
    @TableGenerator(
            name="notice_seq",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "key_name",
            valueColumnName = "next_value",
            pkColumnValue="notice_seq",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID",columnDefinition="NUMBER(19)")
    Long id;

    @Column(name = "NOTICE_DT")
    LocalDateTime notice_dt;

    @Enumerated(EnumType.STRING)
    @Column(name = "NOTICE_LV")
    NoticeLevelType notice_lv;

    @Column(name = "NOTICE_TY")
    String notice_ty;

    @Nationalized
    @Column(name = "NOTICE_TITLE")
    String notice_title;

    @Nationalized
    @Column(name = "NOTICE_CN")
    String notice_cn;

    @Nationalized
    @Column(name = "NOTICE_PAYLOAD")
    String notice_payload;

    @Column(name = "IS_READ")
    boolean is_read=false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getNotice_dt() {
        return notice_dt;
    }

    public void setNotice_dt(LocalDateTime notice_dt) {
        this.notice_dt = notice_dt;
    }

    public NoticeLevelType getNotice_lv() {
        return notice_lv;
    }

    public void setNotice_lv(NoticeLevelType notice_lv) {
        this.notice_lv = notice_lv;
    }

    public String getNotice_ty() {
        return notice_ty;
    }

    public void setNotice_ty(String notice_ty) {
        this.notice_ty = notice_ty;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_cn() {
        return notice_cn;
    }

    public void setNotice_cn(String notice_cn) {
        this.notice_cn = notice_cn;
    }

    public String getNotice_payload() {
        return notice_payload;
    }

    public void setNotice_payload(String notice_payload) {
        this.notice_payload = notice_payload;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
