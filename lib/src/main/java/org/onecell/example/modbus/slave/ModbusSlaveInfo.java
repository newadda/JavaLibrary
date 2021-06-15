package org.onecell.example.modbus.slave;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModbusSlaveInfo {


    private String id;
    private String slave_name;
    private boolean is_tcp;
    private String tcp_ip_address;
    private Integer tcp_port;


    private Map<String, List<ModbusSlaveMap>> tagname_map = new ConcurrentHashMap<>();
    private Map<ModbusFuncType, Map<Integer, ModbusSlaveMap>> func_map = new ConcurrentHashMap<>();

    public ModbusSlaveInfo() {

    }

    public void setIs_tcp(boolean is_tcp) {
        this.is_tcp = is_tcp;
    }

    public void setTcp_ip_address(String tcp_ip_address) {
        this.tcp_ip_address = tcp_ip_address;
    }

    public void setTcp_port(Integer tcp_port) {
        this.tcp_port = tcp_port;
    }

    public boolean isIs_tcp() {
        return is_tcp;
    }

    public String getTcp_ip_address() {
        return tcp_ip_address;
    }

    public Integer getTcp_port() {
        return tcp_port;
    }

    public Map<String, List<ModbusSlaveMap>> getTagname_map() {
        return tagname_map;
    }

    public Map<ModbusFuncType, Map<Integer, ModbusSlaveMap>> getFunc_map() {
        return func_map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlave_name() {
        return slave_name;
    }

    public void setSlave_name(String slave_name) {
        this.slave_name = slave_name;
    }

    public void setMappingList(List<ModbusSlaveMap> mapList) {
        CopyOnWriteArrayList<ModbusSlaveMap> list = new CopyOnWriteArrayList(mapList);
        for (ModbusSlaveMap map : list) {
            setMapping(map);
        }

    }

    public void setMapping(ModbusSlaveMap map) {
        /// 태그값으로 해쉬맵설정
        String tag_name = map.getTag_name();
        List<ModbusSlaveMap> modbusSlaveMaps = tagname_map.get(tag_name);
        if (modbusSlaveMaps == null) {
            modbusSlaveMaps = new LinkedList<>();
            tagname_map.put(tag_name, modbusSlaveMaps);
        }
        modbusSlaveMaps.add(map);


        /// modbus function 과 start address 로 해쉬맵설정
        ModbusFuncType func_type = map.getFunc_type();
        int start_address = map.getStart_address();
        Map<Integer, ModbusSlaveMap> temp = new ConcurrentHashMap();
        Map<Integer, ModbusSlaveMap> address_map = func_map.putIfAbsent(func_type, temp);
        if (address_map == null) {
            address_map = temp;
        }

        address_map.put(start_address, map);
    }

}