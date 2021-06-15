package org.onecell.common.modbus.slave;

import com.intelligt.modbus.jlibmodbus.data.DataHolder;
import com.intelligt.modbus.jlibmodbus.exception.IllegalDataAddressException;
import com.intelligt.modbus.jlibmodbus.exception.IllegalDataValueException;

import java.util.ArrayList;
import java.util.List;

public class ModbusDataHolder  extends DataHolder {
    final List<ModbusEventListener> modbusEventListenerList = new ArrayList<ModbusEventListener>();

    public ModbusDataHolder() {
        // you can place the initialization code here
            /*
            something like that:
            setHoldingRegisters(new SimpleHoldingRegisters(10));
            setCoils(new Coils(128));
            ...
            etc.
             */
    }

    public void addEventListener(ModbusEventListener listener) {
        modbusEventListenerList.add(listener);
    }

    public boolean removeEventListener(ModbusEventListener listener) {
        return modbusEventListenerList.remove(listener);
    }

    @Override
    public void writeHoldingRegister(int offset, int value) throws IllegalDataAddressException, IllegalDataValueException {
        for (ModbusEventListener l : modbusEventListenerList) {
            l.onWriteToSingleHoldingRegister(offset, value);
        }
        super.writeHoldingRegister(offset, value);
    }

    @Override
    public void writeHoldingRegisterRange(int offset, int[] range) throws IllegalDataAddressException, IllegalDataValueException {
        for (ModbusEventListener l : modbusEventListenerList) {
            l.onWriteToMultipleHoldingRegisters(offset, range.length, range);
        }
        super.writeHoldingRegisterRange(offset, range);
    }

    @Override
    public void writeCoil(int offset, boolean value) throws IllegalDataAddressException, IllegalDataValueException {
        for (ModbusEventListener l : modbusEventListenerList) {
            l.onWriteToSingleCoil(offset, value);
        }
        super.writeCoil(offset, value);
    }

    @Override
    public void writeCoilRange(int offset, boolean[] range) throws IllegalDataAddressException, IllegalDataValueException {
        for (ModbusEventListener l : modbusEventListenerList) {
            l.onWriteToMultipleCoils(offset, range.length, range);
        }
        super.writeCoilRange(offset, range);
    }
}
