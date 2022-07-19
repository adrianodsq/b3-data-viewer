package com.adrianodsq.b3data.repository;

import com.adrianodsq.b3data.importer.B3AcoesHist;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface B3AcoesHistRepository extends CrudRepository<B3AcoesHist, Long> {

    List<B3AcoesHist> findByTickerOrderByInfoDateAsc(String ticker);

    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT TICKER FROM B3_ACOES_HISTORICO"
    )
    List<String> findAllTickers();

}
