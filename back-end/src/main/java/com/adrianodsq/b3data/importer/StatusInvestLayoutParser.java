package com.adrianodsq.b3data.importer;

import java.io.File;
import java.io.IOException;
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

import com.adrianodsq.b3data.repository.HistoricalFinancialDataRepositoryFacade;
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

    @Autowired
    private HeaderFieldMapper headerFieldMapper;

    @Autowired
    private MapBasedParserService mapBasedParserService;

    @Autowired
    private HistoricalFinancialDataRepositoryFacade historicalFinancialDataRepositoryFacade;

    @Value("${b3data.data.base-folder}")
    private String baseFolder;

    private static final DateParser DATE_PARSER = FastDateFormat.getInstance("yyyy_MM_dd");

    private static final int FIRST_LINE = 0;

    public ImportDataResult importDataFromFile(String fileName){
        Path p = Paths.get(baseFolder, fileName);

        File input = p.toFile();
        return importDataFromFile(input);
    }


    public ImportDataResult importDataFromFile(File input){
        if(input.exists()){
            return extractDataFromFile(input);
        }else{
            log.warn("Failed to find file={}", input);
            return null;
        }
    }

    private String getFinancialTypeFromFilename(File someFile){
        // example: 2022_06_05-acoes.csv
        // TO-DO test pattern
        return someFile.getName().split("-")[1].split("\\.")[0];
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
        // Infos from filename
        String financialType = getFinancialTypeFromFilename(input);
        Date dateOfInfo = getDateFromFileName(input);

        FinancialDataParser parser = mapBasedParserService.getParser(financialType);
        ImportDataResult result = new ImportDataResult();

        log.info("file={} step=start", input.getName());

        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            List<String> lines = FileUtils.readLines(input, "UTF-8");
            log.debug("Starting file processing lines={}", lines.size());
            for(int currentLineIndex = 0; currentLineIndex < lines.size(); currentLineIndex++){
                if(isHeader(currentLineIndex)){
                    extractHeaderToMapOfFields(input.getName(), headerMap, parser, lines);
                }else{
                    HistoricalFinancialData row = parser.parseData(lines.get(currentLineIndex), headerMap);
                    row.setInfoDate(dateOfInfo);

                    executor.submit(() -> {
                        long start = System.currentTimeMillis();
                        historicalFinancialDataRepositoryFacade.save(row);
                        long end = System.currentTimeMillis();
                        log.debug("file={} row={} step=save exec={}", input.getName(), row.getTicker(), (end-start));
                    });

                    result.addSuccess();
                }
            }
            executor.shutdown();
            try {
                executor.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("Failed to read file={}", input.getName(),  e);
                e.printStackTrace();
            }
            log.info("file={} step=end", input.getName());
        }catch (IOException ioEx){
            log.error("Failed to read file={}", input.getName(), ioEx);
        }
        return result;
    }

    private void extractHeaderToMapOfFields(String filename, Map<Integer, String> headerMap, FinancialDataParser parser, List<String> lines) {
        String[] headers = lines.get(FIRST_LINE).split(";");

        for(int h = 0; h < headers.length; h++){
            headerMap.put(h, trim(headers[h].toLowerCase()));
        }

        if(headers.length != parser.getMapOfFieldsByAnnotation().size()){
            log.warn("m=extractDataFromFile File have different amount of data file={} headers={} objFields={}", filename, headers.length, parser.getMapOfFieldsByAnnotation().size());
        }
    }

    private String trim(String str){
        return StringUtils.trim(str);
    }

    private boolean isHeader(int i){
        return i == 0;
    }

}
