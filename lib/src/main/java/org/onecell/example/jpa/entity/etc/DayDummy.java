package org.onecell.example.jpa.entity.etc;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Subselect("SELECT  (TO_DATE('18000101', 'YYYYMMDD') + ROWNUM-1) AS DAY\n" +
        "FROM DUAL \n" +
        "CONNECT BY level <= ROUND( TO_DATE('30001231', 'YYYYMMDD') - TO_DATE('20000101', 'YYYYMMDD'))")
@Immutable
public class DayDummy {
    @Id
    @Column(name = "DAY")
    LocalDate day;

    public LocalDate getDay() {
        return day;
    }
}
