package com.tdt.historical_prices.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.config.NumberConstants;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.form.HistoricalPriceCreateForm;
import com.tdt.historical_prices.service.HistoricalPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/historical-prices")
public class HistoricalPriceController {
    private final HistoricalPriceService service;

    @GetMapping
    public ResponseEntity<ResponseMetaData> getListHistoricalPrices(
            @RequestParam(value = "page_no", defaultValue = NumberConstants.DEFAULT_PAGEABLE_PAGE_NO) Integer pageNo,
            @RequestParam(value = "page_size", defaultValue = NumberConstants.DEFAULT_PAGEABLE_PAGE_SIZE) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        ResponseMetaData responseMetaData = service.getAllHistoricalPrices(pageable);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PostMapping("/pull-data")
    public ResponseEntity<ResponseMetaData> pullDataFromApiToHistoricalPrice() throws JsonProcessingException {
        ResponseMetaData responseMetaData = service.pullDataFromApiToHistoricalPrice();
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMetaData> createFromApiToHistoricalPrice(@RequestBody HistoricalPriceCreateForm createForm) {
        ResponseMetaData responseMetaData = service.createHistoricalPrices(createForm);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMetaData> getHistoricalPriceById(@PathVariable(value = "id") int id) {
        ResponseMetaData responseMetaData = service.getHistoricalPriceById(id);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/symbol")
    public ResponseEntity<ResponseMetaData> getHistoricalPricesBySymbol(
            @RequestParam(value = "page_no", defaultValue = NumberConstants.DEFAULT_PAGEABLE_PAGE_NO) Integer pageNo,
            @RequestParam(value = "page_size", defaultValue = NumberConstants.DEFAULT_PAGEABLE_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "symbol") String symbol) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        ResponseMetaData responseMetaData = service.getHistoricalPricesBySymbol(pageable, symbol);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PutMapping("/update-all")
    public ResponseEntity<ResponseMetaData> updateHistoricalPriceById() throws JsonProcessingException {
        ResponseMetaData responseMetaData = service.updateAllHistoricalPrice();
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMetaData> deleteHistoricalPriceById(@PathVariable(value = "id") int id) {
        ResponseMetaData responseMetaData = service.deleteHistoricalPriceById(id);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete/{symbol}")
    public ResponseEntity<ResponseMetaData> deleteHistoricalPriceBySymbol(@PathVariable(value = "symbol") String symbol) {
        ResponseMetaData responseMetaData = service.deleteHistoricalPriceBySymbol(symbol);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<ResponseMetaData> deleteAllHistoricalPrice() {
        ResponseMetaData responseMetaData = service.deleteAllHistoricalPrice();
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }
}
