package com.adrianodsq.b3data.web;

import com.adrianodsq.b3data.*;
import com.adrianodsq.b3data.importer.*;
import com.adrianodsq.b3data.repository.*;
import com.google.common.collect.*;
import com.sun.istack.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("b3")
@Slf4j
public class B3DataController {

    @Autowired
    B3AcoesHistRepository b3AcoesHistRepository;

    @Autowired
    B3FiisHistRepository b3FiisHistRepository;

    @Autowired
    StocksHistRepository stocksHistRepository;

    @Autowired
    ReitsHistRepository reitsHistRepository;

    @Autowired
    StatusInvestLayoutParser statusInvestLayoutParser;

    @Value("${b3data.data.base-folder}")
    private String baseFolder;

    // curl http://localhost:8080/b3/acoes/CYRE3
    @GetMapping("/acoes/{ticker}")
    public @ResponseBody List<B3AcoesHist> getAcoesByTicker(@PathVariable("ticker") @NotNull final String ticker){
        return b3AcoesHistRepository.findByTickerOrderByInfoDateAsc(ticker.toUpperCase());

    }

    // curl http://localhost:8080/b3/fiis/HGLG11
    @GetMapping("/fiis/{ticker}")
    public @ResponseBody List<B3FiisHist> getFiisByTicker(@PathVariable("ticker") @NotNull final String ticker){
        return b3FiisHistRepository.findByTickerOrderByInfoDateAsc(ticker.toUpperCase());
    }

    // curl http://localhost:8080/b3/stocks/NVDA
    @GetMapping("/stocks/{ticker}")
    public @ResponseBody List<StocksHist> getStocksByTicker(@PathVariable("ticker") @NotNull final String ticker){
        return stocksHistRepository.findByTickerOrderByInfoDateAsc(ticker.toUpperCase());
    }

    // curl http://localhost:8080/b3/reits/AMT
    @GetMapping("/reits/{ticker}")
    public @ResponseBody List<ReitsHist> getReitsByTicker(@PathVariable("ticker") @NotNull final String ticker){
        return reitsHistRepository.findByTickerOrderByInfoDateAsc(ticker.toUpperCase());
    }

    @GetMapping("/data")
    public @ResponseBody List<B3AcoesHist> getAll(){
        Iterable<B3AcoesHist> dataFromDatabase = b3AcoesHistRepository.findAll();
        List<B3AcoesHist> result = new ArrayList<>();

        dataFromDatabase.forEach(row -> result.add(row));

        return result;
    }

    // curl -X POST http://localhost:8080/b3/import-file/2021_01_09-acoes.csv
    // curl -X POST http://localhost:8080/b3/import-file/{file}
    @PostMapping("/import-file/{file}")
    public ResponseEntity<ImportDataResult> importDataFromFile(@PathVariable("file") String fileName){
        ImportDataResult result = statusInvestLayoutParser.importDataFromFile(fileName);
        if(result == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }


    // curl -X POST http://localhost:8080/b3/{type}/import-all
    @PostMapping("/{type}/import-all")
    public ResponseEntity<List<ImportDataResult>> importAll(@PathVariable("type") String fileType){
        List<ImportDataResult> results = Lists.newArrayList();
        List<Path> filesToProcess = Lists.newArrayList();
        try (Stream<Path> paths = Files.walk(Paths.get(baseFolder), 1)) {
            filesToProcess = paths
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.toFile().isDirectory() && (f.getFileName().toString().contains(fileType)))
                    .sorted(Comparator.comparing(p -> p.toFile().getName()))
                    .collect(Collectors.toList());

            filesToProcess.forEach(f -> statusInvestLayoutParser.importDataFromFile(f.toFile()));
        } catch (IOException ioEx) {
            log.error("Failed to create list of response files", ioEx);
        }
        return ResponseEntity.ok(results);
    }

    // curl http://localhost:8080/b3/tickers
    @GetMapping("/tickers")
    public ResponseEntity<List<String>> getAllTickers(){
        List<String> tickers = b3AcoesHistRepository.findAllTickers();
        if(tickers == null || tickers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickers);
    }

}
