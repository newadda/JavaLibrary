package org.onecell.example.modbus.master;

import java.util.concurrent.ExecutionException;

public interface IModbus {
    void connect() throws ExecutionException, InterruptedException;
    void close();

    /* Bit access */
    boolean[] readDiscreteInputs(int inputAddress, int quantity); // code 02

    boolean[] readCoils(int startingAddress, int quantity); // code 01 //여러개 읽는다.
    void writeSingleCoil(int outputAddress, boolean outValue); // code 05
    void writeMultipleCoil(int startingAddress, int quantity, boolean[] outValue); // code 15 // 여러개 쓰기

    /* 16 bits access */
    short []  readInputRegister(int startingAddress, int quantity); // code 0x04 최대 1~125개

    short []   readHoldingRegisters(int startingAddress, int quantity);//code 0x03
    void writeSingleRegister(int address, int value); // code 0x06
    void writeMultipleRegisters(int startingAddress, int quantity, short[] value); // 0x10
    //readWriteMultipleRegisters()

}
