package restdocs.lib;

import lombok.Getter;
import lombok.Setter;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import javax.validation.Constraint;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public class TransDtoToDescription {

    public enum DataType{
        Number,
        String,
        Date,
        DateTime,
        Object,
        Boolean,
        Varies,
        Array;

    }

    @Getter
    @Setter
    public static class Description {
        private String path;
        private Object type;
        private String description="";
        private List<String> constraints;
        private Boolean isOptional=false;

        public FieldDescriptor createField() {
            FieldDescriptor fieldDescriptor = fieldWithPath(path);
            if(isOptional==true)
            {
                fieldDescriptor.optional();
            }
            return
                    fieldDescriptor.type(type).description(description).attributes(key("constraint")
                            .value(String.join("\n", constraints)));
        }

        public ParameterDescriptor createParam() {
            ParameterDescriptor descriptor = parameterWithName(path);
            if(isOptional==true)
            {
                descriptor.optional();
            }
            return
                    descriptor.description(description).attributes(key("constraint")
                            .value(String.join("\n", constraints)));
        }

    }

    public static List<ParameterDescriptor> createParamList(Map<String, Description> map) {
        List<ParameterDescriptor> list = new LinkedList<>();
        map.forEach((s, description) ->
                list.add(description.createParam())
        );
        return list;
    }




    public static List<FieldDescriptor> createFieldList(Map<String, Description> map) {
        List<FieldDescriptor> list = new LinkedList<>();
        map.forEach((s, description) ->
                list.add(description.createField())
        );
        return list;
    }

    public static Map<String, Description> trans(Class clazz) {
        try {
            return _trans( "",clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Description> _trans(String prefix,Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        Map<String, Description> map = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            Description item = new Description();
            field.setAccessible(true);
            String name = field.getName();

            item.setPath(prefix+name);

            Class<?> type = field.getType();
            DataType jsonType = getTypeName(type);

            item.setType(jsonType.name());

            List<String> constraintList = new LinkedList<>();
            for (Annotation anno : field.getDeclaredAnnotations()) {
                Annotation[] annotations = anno.annotationType().getDeclaredAnnotations();

                Stream<Annotation> annotationStream = Arrays.stream(annotations).filter(
                        annotation -> annotation.annotationType().isAssignableFrom(Constraint.class));
                boolean present = annotationStream.findFirst().isPresent();
                if (present == true) {
                    constraintList.add(getConstraintString(anno));
                } else if (anno.annotationType().isAssignableFrom(Description.class)) {

                    item.setDescription(anno.annotationType().getDeclaredMethod("value").invoke(anno).toString());
                }
            }
            item.setConstraints(constraintList);
            map.put(item.getPath(), item);

            if(jsonType.equals(DataType.Object)) {
                item.setIsOptional(true);
                Map<String, Description> stringDescriptionMap = _trans(item.getPath() + ".", field.getType());

                /// Object 가 null이 될수 있으므로. optional을 준다.
                stringDescriptionMap.forEach((s, description) -> description.setIsOptional(true));
                map.putAll(stringDescriptionMap);
            }else if(jsonType.equals(DataType.Array)){
                item.setIsOptional(true);
                ParameterizedType genericType = (ParameterizedType)field.getGenericType();
                if(genericType.getActualTypeArguments().length>0)
                {
                    Type generic =  genericType.getActualTypeArguments()[0];

                    Map<String, Description> stringDescriptionMap = _trans(item.getPath() + "[].",  getClass(generic));
                    stringDescriptionMap.forEach((s, description) -> description.setIsOptional(true));
                    map.putAll(stringDescriptionMap);
                }

            }
        }

        return map;

    }

    private static DataType getTypeName(Class clazz)
    {


        if( Number.class.isAssignableFrom(clazz))
        {
            return DataType.Number;
        }

        if( String.class.isAssignableFrom(clazz))
        {
            return DataType.String;
        }


        if( Collection.class.isAssignableFrom(clazz))
        {
            return DataType.Array;
        }

        if( Boolean.class.isAssignableFrom(clazz))
        {
            return DataType.Boolean;
        }


        if( Temporal.class.isAssignableFrom(clazz))
        {
            return DataType.DateTime;
        }



        if( clazz.isAssignableFrom(Object.class))
        {
            return DataType.Varies;
        }

        return DataType.Object;

    }


    private static String getConstraintString(Annotation annotation) {
        StringBuilder str = new StringBuilder();
        List<String> exclude = new LinkedList<String>() {{
            add("groups");
            add("payload");
            add("message");
        }};


        Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
        List<Method> collect = Arrays.stream(declaredMethods).filter(method ->
                !(exclude.stream().filter(item -> item.equals(method.getName())).findFirst().isPresent())
        ).collect(Collectors.toList());

        String simpleName = annotation.annotationType().getSimpleName();
        str.append(simpleName + "(");

        List<String> values = new LinkedList<>();
        collect.forEach(method -> {
            try {
                values.add(method.getName() + "=" + method.invoke(annotation));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        String join = String.join(",", values);
        str.append(join + ")");

        return str.toString();
    }
    private static final String TYPE_NAME_PREFIX = "class ";
    public static Class<?> getClass(Type type) throws ClassNotFoundException {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
        }
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    static public String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }
}
