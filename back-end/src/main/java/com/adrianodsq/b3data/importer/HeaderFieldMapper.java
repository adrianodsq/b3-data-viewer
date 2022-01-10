package com.adrianodsq.b3data.importer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class HeaderFieldMapper {

    private Map<String, Field> mapOfFields;

    public Map<String, Field> getMapOfFields(){
        if(mapOfFields == null || mapOfFields.size() == 0){
            mapOfFields = buildMapOfFields();
        }
        return mapOfFields;
    }

    @PostConstruct
    public void loadAnnotations(){
        mapOfFields = buildMapOfFields();
    }

    private Map<String, Field> buildMapOfFields() {
        Field[] fields = B3AcoesHist.class.getDeclaredFields();
        Map<String, Field> mapOfFieldsByAnnotation = new HashMap<>();

        for(int f = 0; f < fields.length; f++){

            Field field = fields[f];
            FinancialData financialData = field.getAnnotation(FinancialData.class);
            if(financialData != null){
                mapOfFieldsByAnnotation.put(financialData.value().toLowerCase().trim(), field);
            }
        }
        return mapOfFieldsByAnnotation;
    }
}
