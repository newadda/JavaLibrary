package org.onecell.example.modbus.master;

import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.*;
import com.digitalpetri.modbus.responses.*;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ModbusTcpIODriver implements IModbus {
    ModbusTcpMaster master;
    int slave_id;

    public ModbusTcpIODriver(String ip, int port, int slave_id, long timeout_ms, int reconnect_count ){
       this.slave_id = slave_id;

        ModbusTcpMasterConfig.Builder builder = new ModbusTcpMasterConfig.Builder(ip);

        builder.setPort(port);
        Duration duration = Duration.ofMillis(timeout_ms);
        builder.setTimeout(duration);
        builder.setMaxReconnectDelaySeconds(reconnect_count);

        ModbusTcpMasterConfig config = builder.build();
        master = new ModbusTcpMaster(config);


    }


    @Override
    public void connect() {
        CompletableFuture<ModbusTcpMaster> connect = master.connect();
        try {
            connect.get();
        } catch (Exception e) {

        }
    }

    @Override
    public void close() {
        master.disconnect();
    }

    private boolean[] bytesToBoolean(ByteBufModbusResponse response, int quantity)
    {
        boolean[] ret = new boolean[quantity];
        ByteBuf content=null;
        try {
            content = response.content();
            int i = content.readableBytes();


            for(int index=0;index<quantity;index++)
            {
                ret[index]=false;

                int byteIndex = Math.floorDiv(index, 8);
                int mod = Math.floorMod(index, 8);
                if(byteIndex<i)
                {
                    byte aByte = content.getByte(byteIndex);
                    if((aByte & (0x01<<mod))==(0x01<<mod))
                    {
                        ret[index]=true;
                    }
                }
            }

       }finally {
            if(content!=null)
            {
                ReferenceCountUtil.release(content);
            }


        }

        return ret;

    }




    @Override
    public boolean[] readDiscreteInputs(int inputAddress, int quantity) {
        ReadDiscreteInputsRequest readDiscreteInputsRequest = new ReadDiscreteInputsRequest(inputAddress, quantity);
        CompletableFuture<ReadDiscreteInputsResponse> future  = master.sendRequest(readDiscreteInputsRequest, slave_id);

        boolean[] ret=null;
        ByteBuf content=null;

        ReadDiscreteInputsResponse response = null;
        try {
            response = future.get();
            ret = bytesToBoolean(response, quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ret;

    }

    @Override
    public boolean[] readCoils(int startingAddress, int quantity)
    {
        ReadCoilsRequest request = new ReadCoilsRequest(startingAddress,quantity);
        CompletableFuture<ReadCoilsResponse> future = master.sendRequest(request, slave_id);
        boolean[] ret=null;
        ByteBuf content=null;

        ReadCoilsResponse response = null;
        try {
            response = future.get();
            ret = bytesToBoolean(response, quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void writeSingleCoil(int outputAddress, boolean outValue) {

    }

    @Override
    public void writeMultipleCoil(int startingAddress, int quantity, boolean[] outValue) {

    }

    @Override
    public short[] readInputRegister(int startingAddress, int quantity) {
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(startingAddress,quantity);
        CompletableFuture<ReadInputRegistersResponse> future = master.sendRequest(request, slave_id);

        short[] ret=null;
        ByteBuf content=null;
        try {
            ReadInputRegistersResponse response = future.get();
            content = response.content();
            int cnt = content.capacity()/2;
            ret = new short[cnt];
            byte[] test = new byte[content.capacity()];
            content.getBytes(0 ,test);
            for(int i=0;i<cnt;i++)
            {
                ret[i]= content.getShort(i*2);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            if(content!=null)
            {
                ReferenceCountUtil.release(content);
            }

        }

        return ret;
    }

    @Override
    public short[] readHoldingRegisters(int startingAddress, int quantity) {

        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(startingAddress,quantity);
        CompletableFuture<ReadHoldingRegistersResponse> future = master.sendRequest(request, slave_id);

        short[] ret=null;
        try {
            ReadHoldingRegistersResponse response = future.get();
            ByteBuf content = response.content();
            int cnt = response.refCnt();
            ret = new short[cnt];
            for(int i=0;i<cnt;i++)
            {
                ret[i]= content.getShort(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void writeSingleRegister(int address, int value) {

    }

    @Override
    public void writeMultipleRegisters(int startingAddress, int quantity, short[] value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(value.length*2);
        for(short v : value)
        {
            byteBuffer.putShort(v);
        }
        byteBuffer.position(0);
        byte[] array = byteBuffer.array();

        WriteMultipleRegistersRequest request = new WriteMultipleRegistersRequest(startingAddress , quantity,array);
        CompletableFuture<WriteMultipleRegistersResponse> future = master.sendRequest(request, slave_id);
        try {
            WriteMultipleRegistersResponse response = future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }





}
