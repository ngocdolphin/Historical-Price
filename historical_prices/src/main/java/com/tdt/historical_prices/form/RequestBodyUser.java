package com.tdt.historical_prices.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RequestBodyUser {
    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;
}
