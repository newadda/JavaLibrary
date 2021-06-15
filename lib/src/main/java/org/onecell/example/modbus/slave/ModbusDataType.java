package org.onecell.example.modbus.slave;

public enum ModbusDataType {
    INT16(true),
    UINT16(true),
    INT32(true),
    UINT32(true),
    INT48(true),
    UINT48(true),
    INT64(true),
    UINT64(true),
    FLOAT32(true),
    BOOLEAN(false);

    private boolean is_number;

    ModbusDataType(boolean is_number)
            {
                this.is_number = is_number;
            }

    public boolean isIs_number() {
        return is_number;
    }


}