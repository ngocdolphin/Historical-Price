package com.tdt.historical_prices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.entity.HistoricalPrice;
import com.tdt.historical_prices.form.HistoricalPriceCreateForm;
import com.tdt.historical_prices.model.Chart;
import com.tdt.historical_prices.model.Symbol;
import com.tdt.historical_prices.repository.HistoricalPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class HistoricalPriceService {
    private final HistoricalPriceRepository repository;
    private final RestTemplate restTemplate;

    public ResponseMetaData createHistoricalPrices(HistoricalPriceCreateForm createForm) {
        HistoricalPrice historicalPrice = new HistoricalPrice(createForm);
        repository.save(historicalPrice);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData getAllHistoricalPrices(Pageable pageable) {
        Page<HistoricalPrice> historicalPrices = repository.findAll(pageable);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), historicalPrices);
    }

    public ResponseMetaData pullDataFromApiToHistoricalPrice() throws JsonProcessingException {
        List<HistoricalPrice> historicalPrices = pullDataFromApi();

        if (CollectionUtils.isEmpty(historicalPrices)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }

        repository.saveAll(historicalPrices);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData getHistoricalPriceById(int id) {
        HistoricalPrice historicalPrice = repository.findAllById(id);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), historicalPrice);
    }

    public ResponseMetaData getHistoricalPricesBySymbol(Pageable pageable, String symbol) {
        Page<HistoricalPrice> historicalPrices = repository.findAllBySymbol(pageable, symbol);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), historicalPrices);
    }

    public ResponseMetaData updateAllHistoricalPrice() throws JsonProcessingException {
        List<HistoricalPrice> prices = repository.findAll();
        List<HistoricalPrice> pricesUpdate = pullDataFromApi();
        if (pricesUpdate == null) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        repository.deleteAll(prices);
        repository.saveAll(pricesUpdate);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData deleteHistoricalPriceById(int id) {
        HistoricalPrice historicalPrice = repository.findAllById(id);
        repository.delete(historicalPrice);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData deleteHistoricalPriceBySymbol(String symbol) {
        List<HistoricalPrice> prices = repository.findAllBySymbol(symbol);
        repository.deleteAll(prices);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData deleteAllHistoricalPrice() {
        List<HistoricalPrice> prices = repository.findAll();
        repository.deleteAll(prices);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public List<HistoricalPrice> pullDataFromApi() throws JsonProcessingException {
        String url =
                "https://cloud.iexapis.com/stable/stock/market/batch?" +
                        "symbols=aapl,aa&types=chart&range=1m&last=5&token=pk_840fb086b8bb4194a5cd11a8caa107fa";
        HttpHeaders headers = new HttpHeaders();
        String output = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> objectMap = mapper.readValue(output, Map.class);
        List<HistoricalPrice> historicalPrices = new ArrayList<>();

        for (String key : objectMap.keySet()) {
            Symbol symbol = mapper.readValue(new ObjectMapper().writer()
                    .withDefaultPrettyPrinter().writeValueAsString(objectMap.get(key)), Symbol.class);

            for (Chart chart : symbol.getChart()) {
                HistoricalPrice historicalPrice = new HistoricalPrice();
                historicalPrice.setClose(chart.getClose());
                historicalPrice.setHigh(chart.getHigh());
                historicalPrice.setLow(chart.getLow());
                historicalPrice.setOpen(chart.getOpen());
                historicalPrice.setPriceDate(chart.getPriceDate());
                historicalPrice.setSymbol(chart.getSymbol());
                historicalPrice.setVolume(chart.getVolume());
                historicalPrice.setKey(chart.getKey());
                historicalPrice.setChangeOverTime(chart.getChangeOverTime());
                historicalPrice.setMarketChangeOverTime(chart.getMarketChangeOverTime());
                historicalPrice.setUOpen(chart.getUOpen());
                historicalPrice.setUClose(chart.getUClose());
                historicalPrice.setUHigh(chart.getUHigh());
                historicalPrice.setULow(chart.getULow());
                historicalPrice.setUVolume(chart.getUVolume());

                historicalPrices.add(historicalPrice);
            }
        }
        return historicalPrices;
    }
}