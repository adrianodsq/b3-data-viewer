package com.adrianodsq.b3data.importer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FiisParserService extends FinancialDataParser<B3FiisHist>{

    public static final String FINANCIAL_TYPE = "FIIS";

    @Override
    public B3FiisHist parseData(String line, Map<Integer, String> headerMap) {
        B3FiisHist row = new B3FiisHist();
        setValue(row, headerMap, line);

        return row;
    }

    public String getFinancialDataType(){
        return FINANCIAL_TYPE;
    }
}
