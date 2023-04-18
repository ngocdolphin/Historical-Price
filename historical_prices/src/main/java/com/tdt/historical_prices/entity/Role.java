package com.tdt.historical_prices.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    EMPLOYEE("EMPLOYEE");

    private String value;

    Role(String value) {
        this.value = value;
    }
}
