package com.tdt.historical_prices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Chart {
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

        @JsonProperty("id")
        private String id;

        @JsonProperty("key")
        private String key;

        @JsonProperty("subkey")
        private String subkey;

        @JsonProperty("date")
        private Date date;

        @JsonProperty("updated")
        private long updated;

        @JsonProperty("changeOverTime")
        private float changeOverTime;

        @JsonProperty("marketChangeOverTime")
        private float marketChangeOverTime;

        @JsonProperty("uOpen")
        private float uOpen;

        @JsonProperty("uClose")
        private float uClose;

        @JsonProperty("uHigh")
        private float uHigh;

        @JsonProperty("uLow")
        private float uLow;

        @JsonProperty("uVolume")
        private long uVolume;

        @JsonProperty("fOpen")
        private float fOpen;

        @JsonProperty("fClose")
        private float fClose;

        @JsonProperty("fHigh")
        private float fHigh;

        @JsonProperty("fLow")
        private float fLow;

        @JsonProperty("fVolume")
        private long fVolume;

        @JsonProperty("label")
        private String label;

        @JsonProperty("change")
        private float change;

        @JsonProperty("changePercent")
        private float changePercent;
}
