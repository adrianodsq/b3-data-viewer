package com.adrianodsq.b3data.repository;

import com.adrianodsq.b3data.importer.B3FiisHist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface B3FiisHistRepository extends CrudRepository<B3FiisHist, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT TICKER FROM B3_FIIS_HISTORICO"
    )
    List<String> findAllTickers();

    List<B3FiisHist> findByTickerOrderByInfoDateAsc(String ticker);
}
