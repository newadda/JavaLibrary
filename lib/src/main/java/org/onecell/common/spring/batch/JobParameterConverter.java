package org.onecell.common.spring.batch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JobParameterConverter {
    ObjectMapper mapper;

    public JobParameterConverter()
    {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        mapper.registerModule(simpleModule);
        mapper.registerModule(new JavaTimeModule());
    }


    public JobParameters toJobParameters(Object o)
    {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        Map<String,Object> map = mapper.convertValue(o, Map.class);
        map.forEach((key, value) -> {
            if(value instanceof Long)
            {
                jobParametersBuilder.addLong(key,(Long)value);
            }else if(value instanceof Integer)
            {
                Integer putValue = (Integer)value;
                jobParametersBuilder.addLong(key,Long.valueOf(putValue));
            }else if(value instanceof String)
            {
                jobParametersBuilder.addString(key,(String)value);
            }else if(value instanceof Double)
            {
                jobParametersBuilder.addDouble(key,(Double)value);
            }else if(value instanceof Float)
            {
                BigDecimal putValue = new BigDecimal((Float) value);
                jobParametersBuilder.addDouble(key,putValue.doubleValue());
            }
        });
        return jobParametersBuilder.toJobParameters();
    }

    public  <T> T fromMap(Map<String,Object> map,Class<T> tClass){
        return mapper.convertValue(map, tClass);
    }

    public <T> T toClass(JobParameters parameters,Class<T> tClass)
    {
        Map<String,Object> map = new HashMap<>();


        Map<String, JobParameter> parameters1 = parameters.getParameters();
        Set<String> kets = parameters1.keySet();
        for(String key : kets)
        {
            Object value = parameters1.get(key).getValue();
            map.put(key,value);
        }

        return fromMap(map,tClass);
    }


}
