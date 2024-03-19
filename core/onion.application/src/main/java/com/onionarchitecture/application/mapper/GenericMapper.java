package com.onionarchitecture.application.mapper;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
@Component
public class GenericMapper {
    public <T, TResult> TResult map(T productDto, Class<TResult> resultType) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        TResult result = resultType.getDeclaredConstructor().newInstance();
        Map<String, Object> fieldValues = getFieldValues(productDto);
        setFieldValues(result, fieldValues);
        return result;
    }

    private <T> Map<String, Object> getFieldValues(T object) throws IllegalAccessException {
        Map<String, Object> fieldValues = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldValues.put(field.getName(), field.get(object));
        }
        return fieldValues;
    }

    private <TResult> void setFieldValues(TResult object, Map<String, Object> fieldValues) throws IllegalAccessException {
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            try {
                Field field = object.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(object, entry.getValue());
            } catch (NoSuchFieldException e) {
            }
        }
    }

}
