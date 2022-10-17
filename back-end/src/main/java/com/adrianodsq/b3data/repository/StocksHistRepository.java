package com.adrianodsq.b3data.repository;

import com.adrianodsq.b3data.importer.B3FiisHist;
import com.adrianodsq.b3data.importer.StocksHist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StocksHistRepository extends CrudRepository<StocksHist, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT TICKER FROM STOCKS_HIST"
    )
    List<String> findAllTickers();

    List<StocksHist> findByTickerOrderByInfoDateAsc(String ticker);
}
