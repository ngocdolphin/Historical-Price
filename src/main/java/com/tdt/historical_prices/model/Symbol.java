package com.tdt.historical_prices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdt.historical_prices.model.Chart;
import lombok.Data;

import java.util.List;

@Data
public class Symbol {
    @JsonProperty("chart")
    private List<Chart> chart;
}
