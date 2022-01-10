package com.adrianodsq.b3data.portfolio;

import org.springframework.data.repository.CrudRepository;

public interface PortfolioRepository  extends CrudRepository<Portfolio, Long> {

    Portfolio findOneByName(String name);
}
