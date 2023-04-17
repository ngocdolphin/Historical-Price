package com.tdt.historical_prices.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdt.historical_prices.entity.Role;
import lombok.Data;

@Data
public class UserUpdateForm {
    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private Role role;
}
