package org.onecell.example.jpa.entity.etc;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Subselect("SELECT  ROWNUM as ROW_NUM, (TO_TIMESTAMP('20000101 00:00:00', 'YYYYMMDD HH24:MI:SS') + numtodsinterval(ROWNUM-1, 'HOUR')  ) AS DATETIME\n" +
        "FROM DUAL \n" +
        "CONNECT BY level <= (ROUND( TO_DATE('30001231', 'YYYYMMDD') - TO_DATE('20000101', 'YYYYMMDD')  )* 24) ")
@Immutable
public class HourDummy {
    @Id
    @Column(name = "ROW_NUM")
    Long row_num;


    @Column(name = "DATETIME")
    LocalDateTime datetime;

    public Long getRow_num() {
        return row_num;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
}
