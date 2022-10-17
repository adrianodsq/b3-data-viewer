package com.adrianodsq.b3data.repository;

import com.adrianodsq.b3data.importer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoricalFinancialDataRepositoryFacade<T extends HistoricalFinancialData> {

    @Autowired
    private B3AcoesHistRepository b3AcoesHistRepository;

    @Autowired
    private B3FiisHistRepository b3FiisHistRepository;

    @Autowired
    private StocksHistRepository stocksHistRepository;

    @Autowired
    private ReitsHistRepository reitsHistRepository;

    public void save(T row){
        if(row instanceof B3FiisHist){
            b3FiisHistRepository.save((B3FiisHist)row);
        }
        if(row instanceof B3AcoesHist){
            b3AcoesHistRepository.save((B3AcoesHist)row);
        }
        if(row instanceof StocksHist){
            stocksHistRepository.save((StocksHist)row);
        }
        if(row instanceof ReitsHist){
            reitsHistRepository.save((ReitsHist)row);
        }
    }
}
