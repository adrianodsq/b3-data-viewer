package com.adrianodsq.b3data.portfolio;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    public Portfolio createPortfolio(String name){
        Portfolio newPortfolio = new Portfolio(name);
        newPortfolio = portfolioRepository.save(newPortfolio);
        return newPortfolio;
    }


}
