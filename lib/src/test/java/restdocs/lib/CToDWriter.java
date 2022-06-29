package restdocs.lib;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CToDWriter {
    List<Item> itemList =new LinkedList<>();

    public CToDWriter(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<FieldDescriptor> createFieldDescriptorList()
    {
        final List<FieldDescriptor> collect = itemList.stream().map(item -> item.createField()).collect(Collectors.toList());
        return collect;

    }



    public List<ParameterDescriptor> createParameterDescriptorList()
    {
        final List<ParameterDescriptor> collect = itemList.stream().map(item -> item.createParam()).collect(Collectors.toList());
        return  collect;
    }



}
