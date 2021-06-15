package org.onecell.example.jpa.entity.etc;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Subselect("SELECT   TO_CHAR((TO_DATE('18000101', 'YYYYMMDD') + NUMTOYMINTERVAL (ROWNUM-1,'MONTH')),'YYYY' )  AS YEAR, TO_CHAR((TO_DATE('19000101', 'YYYYMMDD') + NUMTOYMINTERVAL (ROWNUM-1,'MONTH')),'MM' )  AS MONTH\n" +
        "        FROM DUAL \n" +
        "        CONNECT BY level <= 12*400")
@IdClass(MonthDummy.MonthDummyPK.class)
@Immutable
public class MonthDummy {
    public static class MonthDummyPK implements Serializable{
        private static final long serialVersionUID = 1L;

        private int year;
        private int month;
        public MonthDummyPK() {
            // TODO Auto-generated constructor stub
        }
        public MonthDummyPK(int year, int month)
        {
            this.year = year;
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        @Override
        public int hashCode() {
            return (int)(year ^ month);
        }

        @Override
        public boolean equals(Object o) {
            return ((o instanceof MonthDummyPK) && year == ((MonthDummyPK)o).getYear() && month == ((MonthDummyPK) o).getMonth());
        }
    }
@Id
    @Column(name = "YEAR")
    Integer year;

    @Id
    @Column(name = "MONTH")
    Integer month;

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }
}
