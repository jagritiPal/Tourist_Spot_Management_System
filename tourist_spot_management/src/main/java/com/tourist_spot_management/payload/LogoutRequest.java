package com.tourist_spot_management.payload;

import lombok.Data;

@Data
public class LogoutRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
