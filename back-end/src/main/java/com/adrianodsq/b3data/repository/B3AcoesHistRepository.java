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


    @Query(
            nativeQuery = true,
            value = "SELECT * FROM B3_ACOES_HISTORICO "
                    + "WHERE TICKER = ?1 "
                    + "ORDER BY DAT_INFO DESC "
                    + "LIMIT 2"
    )
    List<B3AcoesHist> findLatestTwoRowsByTicker(String ticker);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM b3_acoes_historico "
                    + "WHERE DAT_INFO IN ("
                    + "SELECT MAX(DAT_INFO) FROM b3_acoes_historico)"
    )
    List<B3AcoesHist> findLatestData();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM b3_acoes_historico "
                    + " WHERE DAT_INFO IN ("
                    + " SELECT MAX(DAT_INFO) FROM b3_acoes_historico)"
    )
    List<B3AcoesHist> findLatestBarganhasData();
}
