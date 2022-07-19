package com.adrianodsq.b3data.importer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeaderFieldMapper {

    private Map<String, Map> mapOfFieldsByType;

    @Autowired
    private ApplicationContext context;

    public Map<String, Field> getMapOfFields(String financialType){
        if(mapOfFieldsByType == null || mapOfFieldsByType.size() == 0){
            loadAnnotations();
        }
        return mapOfFieldsByType.get(financialType);
    }

    @PostConstruct
    public void loadAnnotations(){
        for(Object someBean : getClassesWithFinancialType()){
            Class clazz = someBean.getClass();
            Annotation data = clazz.getAnnotation(FinancialData.class);
            FinancialData financialData = (FinancialData)data;

            log.info("class={}\tvalue={}", clazz.getName(), financialData.value());
            buildMapOfFields(financialData.value(), clazz);
        }
    }

    public Collection<Object> getClassesWithFinancialType(){
        return context.getBeansWithAnnotation(FinancialData.class).values();
    }

    private void buildMapOfFields(String financialType, Class someClazz) {
        if(mapOfFieldsByType == null){
            mapOfFieldsByType = new HashMap<>();
        }
        Map<String, Field> mapOfFieldsByAnnotation = buildMapOfFieldsByClass(someClazz);
        mapOfFieldsByType.put(financialType, mapOfFieldsByAnnotation);
    }

    private Map<String, Field> buildMapOfFieldsByClass(Class someClazz) {
        Field[] fields = someClazz.getDeclaredFields();
        Map<String, Field> mapOfFieldsByAnnotation = new HashMap<>();

        for (Field field : fields) {
            if (isAnnotatedWithFinancialData(field)) {
                mapOfFieldsByAnnotation.put(getIndicatorName(field), field);
            }
        }
        return mapOfFieldsByAnnotation;
    }

    private boolean isAnnotatedWithFinancialData(Field someField){
        return someField.getAnnotation(FinancialData.class) != null;
    }

    private String getIndicatorName(Field someField){
        return  someField.getAnnotation(FinancialData.class).value().toUpperCase().trim();
    }
}
