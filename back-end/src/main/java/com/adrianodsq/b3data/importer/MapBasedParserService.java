package com.adrianodsq.b3data.importer;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class MapBasedParserService {

    @Autowired
    private FinancialDataParser[] financialDataParsers;

    private Map<String, FinancialDataParser> parserByFinancialTypeMap;

    public FinancialDataParser getParser(String financialType){
        if(Strings.isNullOrEmpty(financialType)){
            throw new InvalidParameterException("");
        }else{
            FinancialDataParser parser = parserByFinancialTypeMap.get(financialType.toUpperCase());
            if(parser == null){
                log.error("Failed to find parser type={}", financialType);
                throw new NoSuchElementException(financialType);
            }else{
                return parser;
            }
        }
    }

    @PostConstruct
    public void setup(){
        if(parserByFinancialTypeMap == null && financialDataParsers != null && financialDataParsers.length > 0){
            parserByFinancialTypeMap = new HashMap<>();
            for(int i = 0; i < financialDataParsers.length; i++){
                parserByFinancialTypeMap.put(financialDataParsers[i].getFinancialDataType(), financialDataParsers[i]);
            }
        }
    }
}
