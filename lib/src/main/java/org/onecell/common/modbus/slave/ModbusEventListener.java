package org.onecell.common.modbus.slave;

public interface ModbusEventListener {
    void onWriteToSingleCoil(int address, boolean value);

    void onWriteToMultipleCoils(int address, int quantity, boolean[] values);

    void onWriteToSingleHoldingRegister(int address, int value);

    void onWriteToMultipleHoldingRegisters(int address, int quantity, int[] values);
}
