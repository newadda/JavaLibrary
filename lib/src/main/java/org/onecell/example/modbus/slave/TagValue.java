package org.onecell.example.modbus.slave;

import java.time.LocalDateTime;
import java.util.Map;

public class TagValue<T> {
    String tagKey;
    LocalDateTime datetime;
    T value;
    Map<String, Object> option;

    public TagValue(String tagKey, LocalDateTime datetime, T value) {
        this.tagKey = tagKey;
        this.datetime = datetime;
        this.value = value;

    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Map<String, Object> getOption() {
        return option;
    }

    public void setOption(Map<String, Object> option) {
        this.option = option;
    }
}
