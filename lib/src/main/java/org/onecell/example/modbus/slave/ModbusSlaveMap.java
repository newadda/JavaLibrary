package org.onecell.example.modbus.slave;

public class ModbusSlaveMap {
    private long id;
    private String slave_id;
    private String tag_name;
    private ModbusDataType data_type;
    private ModbusFuncType func_type;
    private int start_address;
    private String byte_order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlave_id() {
        return slave_id;
    }

    public void setSlave_id(String slave_id) {
        this.slave_id = slave_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public ModbusDataType getData_type() {
        return data_type;
    }

    public void setData_type(ModbusDataType data_type) {
        this.data_type = data_type;
    }

    public ModbusFuncType getFunc_type() {
        return func_type;
    }

    public void setFunc_type(ModbusFuncType func_type) {
        this.func_type = func_type;
    }

    public int getStart_address() {
        return start_address;
    }

    public void setStart_address(int start_address) {
        this.start_address = start_address;
    }

    public String getByte_order() {
        return byte_order;
    }

    public void setByte_order(String byte_order) {
        this.byte_order = byte_order;
    }
}