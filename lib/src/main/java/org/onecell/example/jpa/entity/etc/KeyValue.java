package org.onecell.example.jpa.entity.etc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name="WTWS_KEY_VALUE")
public class KeyValue {

    public static final String TYPE_STRING="STRING";
    public static final String TYPE_LONG="LONG";
    public static final String TYPE_DATE="DATE";
    public static final String TYPE_DOUBLE="DOUBLE";

    @Id
    @Column(name = "KEY_NAME")
    private String key_name;

    @Column(name = "DATA_TYPE")
    private String data_type;

    @Column(name = "STRING_VAL")
    private String string_val;

    @Column(name = "DATE_VAL")
    private LocalDateTime date_val;

    @Column(name = "LONG_VAL")
    private Long long_val;

    @Column(name = "DOUBLE_VAL")
    private Double double_val;

    public String getKey_name() {
        return key_name;
    }

    public void setKey_name(String key_name) {
        this.key_name = key_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getString_val() {
        return string_val;
    }

    public void setString_val(String string_val) {
        this.string_val = string_val;
    }

    public LocalDateTime getDate_val() {
        return date_val;
    }

    public void setDate_val(LocalDateTime date_val) {
        this.date_val = date_val;
    }

    public Long getLong_val() {
        return long_val;
    }

    public void setLong_val(Long long_val) {
        this.long_val = long_val;
    }

    public Double getDouble_val() {
        return double_val;
    }

    public void setDouble_val(Double double_val) {
        this.double_val = double_val;
    }
}
