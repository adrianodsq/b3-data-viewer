package com.adrianodsq.b3data.importer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
public abstract class FinancialDataParser<T extends HistoricalFinancialData> {

    @Autowired
    private HeaderFieldMapper headerFieldMapper;

    public abstract T parseData(String line, Map<Integer, String> headerMap);

    public abstract String getFinancialDataType();

    public Map<String, Field> getMapOfFieldsByAnnotation(){
        return headerFieldMapper.getMapOfFields(getFinancialDataType());
    }

    protected void setValue(T row, Map<Integer, String> headerMap, String line){
        String[] values = line.split(";");

        for(int h = 0; h < values.length; h++){
            final String column = headerMap.get(h);
            if(column != null){
                final Field field = getFieldFromColumn(column);
                if (field != null){
                    try {
                        field.setAccessible(true);
                        setFieldValue(row, field, values[h]);
                    } catch (Exception e) {
                        log.error("Failed to parse type={} line={}", getFinancialDataType(), line, e);
                    }
                }else{
                    log.warn("Failed to find field column={} val={} line={}", column, values[h],line);
                }
            }else{
                log.warn("Failed to find field h={} val={} line={}", h, values[h],line);
            }
        }
    }

    private void setFieldValue(T someObject, Field field, String value) throws IllegalAccessException {
        if(field.getType().equals(Float.class)) {
            field.set(someObject, parse(value));
        }else{
            field.set(someObject, value);
        }
    }

    private Field getFieldFromColumn(final String columnName){
        return getMapOfFieldsByAnnotation().get(columnName.toLowerCase());
    }

    // 3.123,67 to 3123.67
    private String removeUnnecessarySeparators(final String value){
        if(StringUtils.isNotBlank(value)){
            String withoutDots = StringUtils.replace(value, ".", "");
            String replacingCommas = StringUtils.replace(withoutDots, ",", ".");

            return replacingCommas;
        }
        return null;
    }

    private Float parse(final String val){
        String result = removeUnnecessarySeparators(val);
        if(result != null)
            return Float.valueOf(result);
        else
            return null;
    }

}
