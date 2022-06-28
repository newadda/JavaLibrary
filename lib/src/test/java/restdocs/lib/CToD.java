package restdocs.lib;

import javax.validation.Constraint;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CToD {






    public static class Item{
        String prefix;
        String name;
        Object Type;
        boolean isOptional=false;
        List<String> desc=new LinkedList<>();
        List<String> constraints=new LinkedList<>();

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

    public static class FieldDesc{
        Item item;
        Class subClass;

        public void setItem(Item item) {
            this.item = item;
        }

        public void setSubClass(Class subClass) {
            this.subClass = subClass;
        }

        public Item getItem() {
            return item;
        }

        public Class getSubClass() {
            return subClass;
        }
    }


    public enum DataType{
        Number,
        String,
        Boolean,
        Object,
        Varies,
        Array;
    }



    private List<Item> _trans(String prefix,Class clazz) throws Exception
    {
        List<Item> ret = new LinkedList<>();

        ///===== 부모 클래스의 필드 목록까지 가져온다.
        Class superclass = clazz.getSuperclass();
        // 부모가 Object 일때까지..
        if(!superclass.isAssignableFrom(Object.class))
        {
            List<Item> superItems = _trans(prefix, superclass);
            ret.addAll(superItems);
        }

        // 자신클래스에 선언된 필드를 가져온다. 부모 필드는 제외한다.
        final Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields)
        {
            final FieldDesc fieldDesc = fieldTrans(prefix, field);
            if(fieldDesc==null)
            {
                continue;
            }

            ret.add(fieldDesc.getItem());

            final Class subClass = fieldDesc.getSubClass();
            if(subClass!=null)
            {
                final Item item = fieldDesc.getItem();
                final List<Item> items = _trans(item.getPath() + "[]", subClass);
                ret.addAll(items);
            }





        }

        return ret;
    }


    private List<String> getConstraintList(Field field)
    {
        List<String> constraintList = new LinkedList<>();
        for (Annotation anno : field.getDeclaredAnnotations()) {
            Annotation[] annotations = anno.annotationType().getDeclaredAnnotations();

            Stream<Annotation> annotationStream = Arrays.stream(annotations).filter(
                    annotation -> annotation.annotationType().isAssignableFrom(Constraint.class));

            boolean present = annotationStream.findFirst().isPresent();
            if (present == true) {
                constraintList.add(getConstraintString(anno));
            }
        }
        return constraintList;
    }

    /**
     * Constraint Annotation의 제약사항을 문자열로 표시한다.
     * 예) @MinMax(min=10,max=99) => "MinMax(min=10,max=99)"
     * @param annotation
     * @return
     */
    private  String getConstraintString(Annotation annotation) {
        StringBuilder str = new StringBuilder();

        // 제외 필드
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

    private FieldDesc fieldTrans(String prefix,Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        FieldDesc fieldDesc = new FieldDesc();
        Item item = new Item();


        fieldDesc.setItem(item);

        item.setPrefix(prefix);

        field.setAccessible(true);//접근 가능하게 설정

        final Annotation[] declaredAnnotations = field.getDeclaredAnnotations(); //선언된 어노테이션 목록 가져옴

        /// 내 어노테이션 가져오기
        final Optional<Annotation> isMyAnnotaion = Arrays.stream(declaredAnnotations).
                filter(annotation -> annotation.annotationType().isAssignableFrom(CToDesc.class)).findFirst();

        if(!isMyAnnotaion.isPresent())
        {
            return null;
        }
        final Annotation myAnnotation = isMyAnnotaion.get();

        final String name = (String) myAnnotation.annotationType().getDeclaredMethod("name").invoke(myAnnotation);
        final String type = (String) myAnnotation.annotationType().getDeclaredMethod("Type").invoke(myAnnotation);
        final Boolean isOptional = (Boolean) myAnnotation.annotationType().getDeclaredMethod("isOptional").invoke(myAnnotation);
        final String value = (String) myAnnotation.annotationType().getDeclaredMethod("value").invoke(myAnnotation);
        final String[] desc = (String[]) myAnnotation.annotationType().getDeclaredMethod("desc").invoke(myAnnotation);
        final String[] constraints = (String[]) myAnnotation.annotationType().getDeclaredMethod("constraints").invoke(myAnnotation);

        item.setName(name.trim().equals("")==true?null:name);
        item.setType(type.trim().equals("")==true?null:type);
        item.setOptional(isOptional);
        item.getDesc().addAll(Arrays.asList(desc));
        item.getConstraints().addAll(Arrays.asList(constraints));

        if(item.getName()==null)
        {
            String fieldName = field.getName();
            item.setName(fieldName);

        }

            // 타입 알아내기

            final DataType myType = getMyType(field.getType());


            if(item.getType()==null)
            {
                item.setType(myType);
            }

            //// Array 의 경우 Array의 element 타입을 구한다.
        Class<?> subClass = null;
        if(myType.equals(DataType.Array))
        {
            // Collection 의 genericType 알아내기
            ParameterizedType genericType = (ParameterizedType)field.getGenericType();
            if(genericType.getActualTypeArguments().length>0) {
                Type generic = genericType.getActualTypeArguments()[0];

                subClass = getClass(generic);
                fieldDesc.setSubClass(subClass);
            }
        }
            if(subClass!=null)
            {
                fieldDesc.setSubClass(subClass);
            }


        /// Constraint Annotation 문자화
        final List<String> constraintList = getConstraintList(field);
        item.getConstraints().addAll(constraintList);


        return fieldDesc;
    }





    private  DataType getMyType(Class clazz)
    {
        List<Class> numberClass = new LinkedList<Class>(){{add(Number.class);}};
        List<Class> stringClass = new LinkedList<Class>(){{add(String.class);add(Temporal.class);}};
        List<Class> collectionClass = new LinkedList<Class>(){{add(Collection.class);}};
        List<Class> booleanClass = new LinkedList<Class>(){{add(Boolean.class);}};
      //  List<Class> temporalClass= new LinkedList<Class>(){{add(Temporal.class);}};



        Function<Class,DataType> numberFunction = target -> {
            final Optional<Class> any = numberClass.stream().filter(source -> source.isAssignableFrom(target)).findAny();
            return any.isPresent()==true? DataType.Number:null;
        };
        Function<Class,DataType> stringFunction = target -> {
            final Optional<Class> any = stringClass.stream().filter(source -> source.isAssignableFrom(target)).findAny();
            return any.isPresent()==true? DataType.String:null;
        };
        Function<Class,DataType> collectionFunction = target -> {
            final Optional<Class> any = collectionClass.stream().filter(source -> source.isAssignableFrom(target)).findAny();
            return any.isPresent()==true? DataType.Array:null;
        };

        Function<Class,DataType> booleanFunction = target -> {
            final Optional<Class> any = booleanClass.stream().filter(source -> source.isAssignableFrom(target)).findAny();
            return any.isPresent()==true? DataType.Boolean:null;
        };

        final List<Function<Class, DataType>> functions = Arrays.asList(numberFunction, stringFunction, collectionFunction, booleanFunction);
        for(Function<Class, DataType> f:functions)
        {
            final DataType apply = f.apply(clazz);
            if(apply!=null)
            {
                return apply;
            }
        }

        if( clazz.isAssignableFrom(Object.class))
        {
            return DataType.Varies;
        }

        return DataType.Object;

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
