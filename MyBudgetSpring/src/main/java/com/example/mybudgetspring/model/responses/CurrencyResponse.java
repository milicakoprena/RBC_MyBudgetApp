package com.example.mybudgetspring.model.responses;

import lombok.Data;

@Data
public class CurrencyResponse {
    private String id;

    private String name;

    public CurrencyResponse() {
    }


    public CurrencyResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
