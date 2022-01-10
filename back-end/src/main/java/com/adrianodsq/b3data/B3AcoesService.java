package com.adrianodsq.b3data;

import com.adrianodsq.b3data.importer.*;
import com.adrianodsq.b3data.repository.B3AcoesHistRepository;
import java.util.*;
import lombok.extern.slf4j.*;
import org.checkerframework.checker.units.qual.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class B3AcoesService {

    @Autowired
    private B3AcoesHistRepository b3AcoesHistRepository;

    public Map<String, List<String>> findDistortionsOnNewerData(){
        List<String> tickers = b3AcoesHistRepository.findAllTickers();

        Map<String, List<String>> mapOfDistortions = new HashMap<>();
        for(String ticker : tickers){
            List<B3AcoesHist> latestTwoRows = b3AcoesHistRepository.findLatestTwoRowsByTicker(ticker);
            if(latestTwoRows != null && latestTwoRows.size() == 2){
                B3AcoesHist current = latestTwoRows.get(0);
                B3AcoesHist previous = latestTwoRows.get(1);
                List<String> distortions = findDistortions(current, previous);
                if(distortions != null && distortions.size() > 0){
                    mapOfDistortions.put(ticker, distortions);
                }
            }else{
                log.warn("Failed to fetch ticker={}", ticker);
            }
        }
        return mapOfDistortions;
    }

    public List<String> findDistortions(B3AcoesHist current, B3AcoesHist previous){
        List<String> attributesWithDistortion = new ArrayList<>();

        Float dyRatio, priceRatio;


        dyRatio = ratio(current.getDividendYield(), previous.getDividendYield());
        priceRatio = ratio(current.getPreco(), previous.getPreco());

        if(priceRatio <= 1.0 &&  current.getValorPorAcao() > previous.getValorPorAcao()){
            attributesWithDistortion.add("VPA");
        }


        if(dyRatio > 1.0 && dyRatio > priceRatio){
            attributesWithDistortion.add("DY");
        }

        return attributesWithDistortion;

    }

    private float ratio(Float current, Float previous){
        if(current != null && previous != null){
            return current / previous;
        }else{
            return -1.0f;
        }
    }

}
