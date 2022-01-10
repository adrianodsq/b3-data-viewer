package com.adrianodsq.b3data.portfolio;

import com.adrianodsq.b3data.repository.B3AcoesHistRepository;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import java.util.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/portolio")
@Slf4j
public class PortfolioController {

    @Autowired
    private B3AcoesHistRepository b3AcoesHistRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    // curl http://localhost:8080/portolio/
    @GetMapping("/")
    public ResponseEntity<List<Portfolio>> findAll(){
        Iterable<Portfolio> all = portfolioRepository.findAll();
        List<Portfolio> portfolios = Lists.newArrayList();
        for(Portfolio p : all){
            portfolios.add(p);
        }
        return ResponseEntity.ok(portfolios);
    }

    // curl -X PUT http://localhost:8080/portolio/add/Watchlist/PCAR3
    // curl -X PUT http://localhost:8080/portolio/add/Carteira/b3sa3
    // curl -X PUT http://localhost:8080/portolio/add/{portfolioName}/{ticker}
    @PutMapping("/add/{portfolioName}/{ticker}")
    public ResponseEntity<Portfolio> addTickerToPortfolio(@PathVariable("portfolioName") String portfolioName,
            @PathVariable("ticker") String ticker){

        Portfolio portfolio = portfolioRepository.findOneByName(portfolioName);
        log.info("step=start p={}", portfolio);
        if(portfolio == null){
            portfolio = new Portfolio(portfolioName);
        }
        portfolio.addTicker(ticker.toUpperCase());
        portfolio = portfolioRepository.save(portfolio);
        log.info("step=end p={}", portfolio);
        return ResponseEntity.ok(portfolio);
    }

    // beef3,itsa4,neoe3,sapr11,taee11,hbsa3,vvar3,wizs3
    // curl -X PUT http://localhost:8080/portolio/add-bulk/Carteira?tickers=beef3,itsa4,neoe3,sapr11,taee11,hbsa3,vvar3,wizs3
    @PutMapping("/add-bulk/{portfolioName}")
    public ResponseEntity<Portfolio> bulkAddToPortfolio(@PathVariable("portfolioName") String portfolioName,
            @RequestParam("tickers") String tickersInput){
        log.info("M=bulkAddToPortfolio\tstep=start\tport={}\ttickers={}", portfolioName, tickersInput);
        ResponseEntity responsePortfolio = null;
        if(!Strings.isNullOrEmpty(tickersInput)){
            String[] tickerList = tickersInput.split(",");
            for(String ticker : tickerList){
                responsePortfolio = addTickerToPortfolio(portfolioName, ticker);
            }
        }else{
            log.warn("Failed to parse input in={}", tickersInput);
            return ResponseEntity.badRequest().build();
        }
        log.info("M=bulkAddToPortfolio\tstep=end\tport={}\ttickers={}", portfolioName, tickersInput);
        return responsePortfolio;
    }

    // curl -X PUT http://localhost:8080/portolio/edit/Carteira?tickers=beef3,itsa4,neoe3,sapr11,taee11,hbsa3,vvar3,wizs3
    @PutMapping("edit/{portfolioName}")
    public ResponseEntity<Portfolio> editPortfolio(@PathVariable("portfolioName") String portfolioName,
                                                   @RequestParam("tickers") String tickersInput){
        ResponseEntity responsePortfolio = null;
        Portfolio portfolio = portfolioRepository.findOneByName(portfolioName);
        log.info("M=editPortfolio\tstep=start\tp={} t={}", portfolio,tickersInput);
        if(portfolio != null && !Strings.isNullOrEmpty(tickersInput)){
            String[] tickerArray = tickersInput.split(",");
            List<String> tickerList = new ArrayList<>();
            List<String> allTickers = b3AcoesHistRepository.findAllTickers();
            //Verifica se todos os tickers informados existem
            for(String someTicker : tickerArray){
                if(tickerExists(someTicker, allTickers))
                    // E adiciona todos os existentes
                tickerList.add(someTicker);
            }
            portfolio.setTickerList(tickerList);
            portfolio = portfolioRepository.save(portfolio);
            return ResponseEntity.ok(portfolio);
        }else{
            log.warn("Failed to parse input in={}", tickersInput);
            return ResponseEntity.badRequest().build();
        }

    }

    private boolean tickerExists(String someTicker, List<String> allTickers){
        return allTickers.contains(someTicker.toUpperCase());
    }

    // curl -X PUT http://localhost:8080/portolio/add/{portfolioName}/{ticker}
    @PutMapping("/remove/{portfolioName}/{ticker}")
    public ResponseEntity<Portfolio> removeTickerToPortfolio(@PathVariable("portfolioName") String portfolioName,
            @PathVariable("ticker") String ticker){

        Portfolio portfolio = portfolioRepository.findOneByName(portfolioName);
        if(portfolio == null){
            portfolio = new Portfolio(portfolioName);
        }
        portfolio.removeTicker(ticker);
        portfolio = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(portfolio);
    }

    // curl -X DELETE http://localhost:8080/portolio/delete/{portfolioName}
    @DeleteMapping("/delete/{portfolioName}")
    public ResponseEntity deletePortfolio(@PathVariable("portfolioName") String portfolioName){
        Portfolio portfolio = portfolioRepository.findOneByName(portfolioName);
        log.info("step=start p={}", portfolioName);
        if(portfolio != null){
            portfolioRepository.delete(portfolio);
        }
        log.info("step=end p={}", portfolioName);
        return ResponseEntity.accepted().build();
    }

}
