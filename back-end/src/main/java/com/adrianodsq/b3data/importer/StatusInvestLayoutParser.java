package com.adrianodsq.b3data.importer;

import com.adrianodsq.b3data.repository.B3AcoesHistRepository;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StatusInvestLayoutParser {

    @Autowired B3AcoesHistRepository b3AcoesHistRepository;

    @Autowired
    HeaderFieldMapper headerFieldMapper;

    @Value("${b3data.data.base-folder}")
    private String baseFolder;

    private static final DateParser DATE_PARSER = FastDateFormat.getInstance("yyyy_MM_dd");

    public ImportDataResult importDataFromFile(String fileName){
        Path p = Paths.get(baseFolder, fileName);

        File input = p.toFile();
        return importDataFromFile(input);
    }

    public  ImportDataResult importDataFromFile(File input){
        if(input.exists()){
            return extractDataFromFile(input);
        }else{
            return null;
        }

    }

    private Date getDateFromFileName(File input){
        // TO-DO test for pattern before parse
        if(input != null){
            String dateFromFilename = input.getName().split("-")[0];
            try{
                return DATE_PARSER.parse(dateFromFilename);
            }catch (ParseException parseException){
                log.error("Failed to parse filename ex={}", parseException);
                return new Date();
            }
        }else{
            return new Date();
        }
    }

    public ImportDataResult extractDataFromFile(File input){
        Map<Integer, String> headerMap = new HashMap<>();
        Map<String, Field> mapOfFieldsByAnnotation = headerFieldMapper.getMapOfFields();
        ImportDataResult result = new ImportDataResult();

        log.info("file={} step=start", input.getName());
        Date dateOfInfo = getDateFromFileName(input);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            List<String> lines = FileUtils.readLines(input, "UTF-8");
            log.debug("Starting file processing lines={}", lines.size());
            for(int currentLineIndex = 0; currentLineIndex < lines.size(); currentLineIndex++){
                if(isHeader(currentLineIndex)){
                    String[] headers = lines.get(currentLineIndex).split(";");

                    for(int h = 0; h < headers.length; h++){
                        headerMap.put(h, trim(headers[h].toLowerCase()));
                    }

                    if(headers.length != mapOfFieldsByAnnotation.size()){
                        log.warn("m=extractDataFromFile File have different amount of data file={} headers={} objFields={}", input.getName(), headers.length, mapOfFieldsByAnnotation.size());
                    }
                }else{

                    B3AcoesHist row = new B3AcoesHist();
                    row.setInfoDate(dateOfInfo);
                    setValue(row, headerMap, mapOfFieldsByAnnotation, currentLineIndex, lines.get(currentLineIndex));
                    result.addSuccess();

                    executor.submit(() -> {
                        long start = System.currentTimeMillis();
                        b3AcoesHistRepository.save(row);
                        long end = System.currentTimeMillis();
                        //log.info("file={} row={} step=save exec={}", input.getName(), row.getTicker(), (end-start));
                    });
                }
            }
            executor.shutdown();
            try {
                executor.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("Failed to read file ex={}",  e);
                e.printStackTrace();
            }
            log.info("file={} step=end", input.getName());
        }catch (IOException ioEx){
            log.error("Failed to read file ex={}", ioEx);
        }
        return result;
    }

    private String trim(String str){
        return StringUtils.trim(str);
    }

    private void setValue(B3AcoesHist row, Map <Integer, String> headerMap, Map<String, Field> mapOfFieldsByAnnotation, int index, String line){
        String[] values = line.split(";");

        for(int h = 0; h < values.length; h++){
            final String column = headerMap.get(h);
            if(column != null){
                final Field field = mapOfFieldsByAnnotation.get(column.toLowerCase());
                if (field != null){
                    try {
                        field.setAccessible(true);
                        if(field.getType().equals(Float.class)) {
                            field.set(row, toDatabase(values[h]));
                        }else{
                            field.set(row, values[h]);
                        }
                    } catch (Exception e) {
                        log.error("{}", e);
                    }
                }else{
                    log.warn("Failed to find field column={} val={} line={}", column, values[h],line);
                }
            }else{
                System.out.println("Failed to find column " + column);
            }
        }

    }

    // 3.123,67 to 3123.67
    private String parseNumberToDatabaseFormat(final String value){
        if(StringUtils.isNotBlank(value)){
            String withoutDots = StringUtils.replace(value, ".", "");
            String replacingCommas = StringUtils.replace(withoutDots, ",", ".");

            return replacingCommas;
        }
        return null;
    }

    private boolean isHeader(int i){
        return i == 0;
    }

    private Float toDatabase(final String val){
        String result = parseNumberToDatabaseFormat(val);
        if(result != null)
            return Float.valueOf(result);
        else
            return null;
    }


}
