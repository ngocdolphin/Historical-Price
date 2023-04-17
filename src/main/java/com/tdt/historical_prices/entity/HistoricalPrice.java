package com.tdt.historical_prices.entity;

import com.tdt.historical_prices.form.HistoricalPriceCreateForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historical_prices")
public class HistoricalPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "close")
    private float close;

    @Column(name = "high")
    private float high;

    @Column(name = "low")
    private float low;

    @Column(name = "open")
    private float open;

    @Column(name = "price_date")
    private Date priceDate;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "volume")
    private long volume;

    @Column(name = "key")
    private String key;

    @Column(name = "change_over_time")
    private float changeOverTime;

    @Column(name = "market_change_over_time")
    private float marketChangeOverTime;

    @Column(name = "u_open")
    private float uOpen;

    @Column(name = "u_close")
    private float uClose;

    @Column(name = "u_high")
    private float uHigh;

    @Column(name = "u_low")
    private float uLow;

    @Column(name = "u_volume")
    private long uVolume;

    public HistoricalPrice(HistoricalPriceCreateForm createForm) {
        this.close = createForm.getClose();
        this.high = createForm.getHigh();
        this.low = createForm.getLow();
        this.open = createForm.getOpen();
        this.priceDate = createForm.getPriceDate();
        this.symbol = createForm.getSymbol();
        this.volume = createForm.getVolume();
        this.key = createForm.getKey();
    }
}
