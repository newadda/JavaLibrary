package restdocs.lib;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public  class Item{
    String prefix;
    String name;
    Object Type;
    boolean isOptional=false;
    List<String> desc=new LinkedList<>();
    List<String> constraints=new LinkedList<>();


    private static final String CONSTRAINT_KEY="constraint";


    private String descriptionStr()
    {
        return String.join("\n", desc);
    }
    private String constraintsStr()
    {
        return String.join("\n", constraints);
    }

    public FieldDescriptor createField() {

        FieldDescriptor fieldDescriptor = fieldWithPath(getPath());
        if(isOptional==true)
        {
            fieldDescriptor.optional();
        }

        return
                fieldDescriptor.type(this.Type.toString()).description(descriptionStr()).attributes(key(CONSTRAINT_KEY)
                        .value(constraintsStr()));
    }

    public ParameterDescriptor createParam() {
        ParameterDescriptor descriptor = parameterWithName(getPath());
        if(isOptional==true)
        {
            descriptor.optional();
        }
        return
                descriptor.description(descriptionStr()).attributes(key(CONSTRAINT_KEY)
                        .value(constraintsStr()));
    }

    public String getPath()
    {
        if(prefix!=null)
        {
            return String.join(".",prefix,name);
        }else {
            return name;
        }

    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getType() {
        return Type;
    }

    public void setType(Object type) {
        Type = type;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    public List<String> getDesc() {
        return desc;
    }

    public List<String> getConstraints() {
        return constraints;
    }
}
