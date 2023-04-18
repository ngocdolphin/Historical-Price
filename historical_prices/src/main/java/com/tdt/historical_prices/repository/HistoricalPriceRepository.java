package com.tdt.historical_prices.repository;

import com.tdt.historical_prices.entity.HistoricalPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricalPriceRepository extends JpaRepository<HistoricalPrice, Integer> {
    Page<HistoricalPrice> findAll(Pageable pageable);

    Page<HistoricalPrice> findAllBySymbol(Pageable pageable, String symbol);

    List<HistoricalPrice> findAllBySymbol(String symbol);

    HistoricalPrice findAllById(int id);
}
