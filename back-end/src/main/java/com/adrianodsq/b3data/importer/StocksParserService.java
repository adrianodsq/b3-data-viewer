package com.adrianodsq.b3data.importer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StocksParserService extends FinancialDataParser<StocksHist>{

    public static final String FINANCIAL_TYPE = "STOCKS";

    @Override
    public StocksHist parseData(String line, Map<Integer, String> headerMap){
        StocksHist row = new StocksHist();
        setValue(row, headerMap, line);

        return row;
    }


    public String getFinancialDataType(){
        return FINANCIAL_TYPE;
    }
}
