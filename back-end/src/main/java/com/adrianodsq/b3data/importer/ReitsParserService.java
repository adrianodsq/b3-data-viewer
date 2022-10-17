package com.adrianodsq.b3data.importer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReitsParserService extends FinancialDataParser<ReitsHist>{

    public static final String FINANCIAL_TYPE = "REITS";

    @Override
    public ReitsHist parseData(String line, Map<Integer, String> headerMap) {
        ReitsHist row = new ReitsHist();
        setValue(row, headerMap, line);

        return row;
    }

    public String getFinancialDataType(){
        return FINANCIAL_TYPE;
    }
}
