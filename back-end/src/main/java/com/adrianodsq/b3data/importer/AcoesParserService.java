package com.adrianodsq.b3data.importer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AcoesParserService extends FinancialDataParser<B3AcoesHist>{

    public static final String FINANCIAL_TYPE = "ACOES";

    @Override
    public B3AcoesHist parseData(String line, Map<Integer, String> headerMap){
        B3AcoesHist row = new B3AcoesHist();
        setValue(row, headerMap, line);

        return row;
    }

    public String getFinancialDataType(){
        return FINANCIAL_TYPE;
    }

}
