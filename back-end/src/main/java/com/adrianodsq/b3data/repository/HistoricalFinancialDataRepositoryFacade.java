package com.adrianodsq.b3data.repository;

import com.adrianodsq.b3data.importer.B3AcoesHist;
import com.adrianodsq.b3data.importer.B3FiisHist;
import com.adrianodsq.b3data.importer.HistoricalFinancialData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoricalFinancialDataRepositoryFacade<T extends HistoricalFinancialData> {

    @Autowired
    private B3AcoesHistRepository b3AcoesHistRepository;

    @Autowired
    private  B3FiisHistRepository b3FiisHistRepository;

    public void save(T row){
        if(row instanceof B3FiisHist){
            b3FiisHistRepository.save((B3FiisHist)row);
        }
        if(row instanceof B3AcoesHist){
            b3AcoesHistRepository.save((B3AcoesHist)row);
        }
    }
}
