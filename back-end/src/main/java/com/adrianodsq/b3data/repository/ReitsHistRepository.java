package com.adrianodsq.b3data.repository;

import com.adrianodsq.b3data.importer.ReitsHist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReitsHistRepository extends CrudRepository<ReitsHist, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT TICKER FROM REITS_HIST"
    )
    List<String> findAllTickers();

    List<ReitsHist> findByTickerOrderByInfoDateAsc(String ticker);
}
