package com.tdt.historical_prices.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class HistoricalPriceCreateForm {
    @JsonProperty("close")
    private float close;

    @JsonProperty("high")
    private float high;

    @JsonProperty("low")
    private float low;

    @JsonProperty("open")
    private float open;

    @JsonProperty("priceDate")
    private Date priceDate;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("volume")
    private long volume;

    @JsonProperty("key")
    private String key;
}
