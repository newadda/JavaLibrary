package org.onecell.example.modbus.slave;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.data.ModbusCoils;
import com.intelligt.modbus.jlibmodbus.data.ModbusHoldingRegisters;
import com.intelligt.modbus.jlibmodbus.exception.IllegalDataAddressException;
import com.intelligt.modbus.jlibmodbus.exception.IllegalDataValueException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlave;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlaveFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.onecell.common.misc.ByteConverter;
import org.onecell.common.misc.NumberToConverterUtil;
import org.onecell.common.misc.Utility;
import org.onecell.common.modbus.slave.ModbusDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class CollectorModbusSlave  {


    public static final Logger LOG = LoggerFactory.getLogger(CollectorModbusSlave.class);

    public static final int READ_TIMEOUT=6000; // millisecond


    volatile ModbusSlave  modbusSlaveTCP;


    ModbusSlaveInfo modbusSlaveInfo;


    volatile ModbusDataHolder modbusDataHolder;
    volatile ModbusCoils discreteInputsData;
    volatile ModbusCoils coilsData;
    volatile ModbusHoldingRegisters inputRegistersData;
    volatile ModbusHoldingRegisters holdingRegistersData;
    public CollectorModbusSlave(@NotNull ModbusSlaveInfo modbusSlaveInfo) {
        this.modbusSlaveInfo = modbusSlaveInfo;

    }

    public ModbusSlaveInfo getModbusSlaveInfo() {
        return modbusSlaveInfo;
    }

    public void start() throws ModbusIOException {
        // 메모리맵 초기화
        modbusDataHolder = new ModbusDataHolder();
        discreteInputsData = new ModbusCoils(Modbus.MAX_START_ADDRESS);

        coilsData = new ModbusCoils(Modbus.MAX_START_ADDRESS);
        inputRegistersData = new ModbusHoldingRegisters(Modbus.MAX_START_ADDRESS);
        holdingRegistersData = new ModbusHoldingRegisters(Modbus.MAX_START_ADDRESS);


        modbusDataHolder.setDiscreteInputs(discreteInputsData);
        modbusDataHolder.setCoils(coilsData);
        modbusDataHolder.setInputRegisters(inputRegistersData);
        modbusDataHolder.setHoldingRegisters(holdingRegistersData);

        if(modbusSlaveInfo.isIs_tcp())
        {

            TcpParameters tcpParameters = new TcpParameters(modbusSlaveInfo.getTcp_ip_address(), modbusSlaveInfo.getTcp_port(), false);
            modbusSlaveTCP = ModbusSlaveFactory.createModbusSlaveTCP(tcpParameters);
            modbusSlaveTCP.setReadTimeout(READ_TIMEOUT);
            modbusSlaveTCP.setDataHolder(modbusDataHolder);
            modbusSlaveTCP.setServerAddress(1);
            modbusSlaveTCP.listen();

        }

    }

    public void stop()
    {
        try {
            if (modbusSlaveTCP != null && modbusSlaveTCP.isListening()) {

                modbusSlaveTCP.shutdown();

            }
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }

    }

    private List<ModbusSlaveMap> findSlaveMap(String tag_name)
    {
        List<ModbusSlaveMap> modbusSlaveMaps = modbusSlaveInfo.getTagname_map().get(tag_name);
        if(modbusSlaveMaps==null)
        {
            return new LinkedList<>();
        }
        return modbusSlaveMaps;
    }

    /// 정수를 바이트 배열로 변환
    private byte[] convertNumberToByteArray(Number number, ModbusDataType modbusDataType , String byte_order)
    {

        boolean is_signed = false;
        int byteSize=0;
        ByteConverter byteConverter = new ByteConverter();

        switch (modbusDataType)
        {
            case INT16 :
            case INT32:
            case INT48:
            case INT64:
            case FLOAT32:
                is_signed = true;
                break;
            case UINT16 :
            case UINT32:
            case UINT48:
            case UINT64:
                is_signed = false;
                break;
            default:
                throw new RuntimeException("disable convert");
        }

        switch (modbusDataType)
        {
            case INT16 :
            case UINT16 :
                byteSize=2;
                break;
            case INT32:
            case UINT32:
            case FLOAT32:
                byteSize=4;
                break;
            case INT48:
            case UINT48:
                byteSize=6;
                break;
            case INT64:
            case UINT64:
                byteSize=8;
                break;
            default:
                throw new RuntimeException("disable convert");
        }
        byte[] bytes=null;
        if(modbusDataType == ModbusDataType.FLOAT32)
        {
            bytes = byteConverter.floatToBytes(number.floatValue(),byte_order);
        }else {
            BigInteger  bigInteger= NumberToConverterUtil.numberToBigInteger(number);
             bytes = byteConverter.bigIntegerToBytes(bigInteger, byte_order);
        }


        return bytes;
    }



    private void setInputRegisters(int startAddress,byte[] bytes) throws IllegalDataAddressException, IllegalDataValueException {
        int[] values = new int[bytes.length/2];

        for(int i =0 ;i<bytes.length;i=i+2) {
            int value = (0x0000ff00 & (bytes[i]<<8)) |(0x000000ff & bytes[i+1]);
            values[i/2]=value;

        }
        inputRegistersData.setRange(startAddress,values);

    }

    private void setHoldingRegisters(int startAddress,byte[] bytes) throws IllegalDataAddressException, IllegalDataValueException
    {
        int[] values = new int[bytes.length/2];
        for(int i =0 ;i<bytes.length;i=i+2) {
            int value = (0x0000ff00 & (bytes[i]<<8)) |(0x000000ff & bytes[i+1]);
            values[i/2]=value;
           // holdingRegistersData.set(startAddress+(i/2),value);
        }
        holdingRegistersData.setRange(startAddress,values);

    }



/*
    /// 태그를 읽고 메모리맵을 갱신한다.
    @Override
    public void subscribe(Message data) {

        // TagValue인지 확인
        if(!(data.getMessage() instanceof TagValue))
        {
            return;
        }

        TagValue tagValue =  (TagValue)data.getMessage();
        try {
            processs(tagValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    */
    private void processs(TagValue tagValue) throws IllegalDataAddressException, IllegalDataValueException {

        String tagKey = tagValue.getTagKey();
        Object value = tagValue.getValue();

        // mapping 값이 존재하는지 확인
        List<ModbusSlaveMap> slaveMap = findSlaveMap(tagKey);
        if(slaveMap == null || slaveMap.size()==0)
        {
            return;
        }



        for(ModbusSlaveMap map:slaveMap)
        {
            ModbusDataType data_type = map.getData_type();
            String byte_order = map.getByte_order();
            // 타입이 숫자형일때
            if(data_type.isIs_number())
            {
                LOG.debug("is number");
                byte[] bytes = convertNumberToByteArray((Number) value, data_type, byte_order);
                LOG.debug("number to bytes = "+ Utility.byteArrayToHex(bytes));

                if(map.getFunc_type().equals(ModbusFuncType.INPUT_REGISTERS))
                {
                    setInputRegisters(map.getStart_address(),bytes);
                }else if(map.getFunc_type().equals(ModbusFuncType.HOLDING_REGISTERS))
                {
                    setHoldingRegisters(map.getStart_address(),bytes);
                }

            }else if(data_type.equals(ModbusDataType.BOOLEAN))
            {
                // Todo 타입이 부울형일때

            }


        }

    }









}
