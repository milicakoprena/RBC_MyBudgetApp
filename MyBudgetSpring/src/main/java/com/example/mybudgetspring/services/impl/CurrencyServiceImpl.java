package com.example.mybudgetspring.services.impl;

import com.example.mybudgetspring.model.responses.CurrencyResponse;
import com.example.mybudgetspring.services.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final ModelMapper modelMapper;
    private final WebClient webClient;

    public CurrencyServiceImpl(ModelMapper modelMapper, WebClient webClient) {
        this.modelMapper = modelMapper;
        this.webClient = webClient;
    }

    @Override
    public List<CurrencyResponse> getAll() {
        Map<String, String> currencyMap = webClient.get()
                .uri("/currencies.json")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .block();

        List<CurrencyResponse> currencyResponses = new ArrayList<>();
        if (currencyMap != null) {
            for (Map.Entry<String, String> entry : currencyMap.entrySet()) {
                String id = entry.getKey();
                String name = entry.getValue();

                if (name != null && !name.isEmpty()) {
                    CurrencyResponse currencyResponse = new CurrencyResponse();
                    currencyResponse.setId(id);
                    currencyResponse.setName(name);
                    currencyResponses.add(currencyResponse);
                }
            }
        }
        return currencyResponses;
    }

    @Override
    public double convertCurrency(String fromCurrency, String toCurrency) throws JsonProcessingException {
        String jsonResponse = webClient.get()
                .uri("/currencies/" + fromCurrency + ".json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);

        JsonNode currencyRates = rootNode.get(fromCurrency.toLowerCase());

        if (currencyRates == null || !currencyRates.has(toCurrency.toLowerCase())) {
            throw new IllegalArgumentException("Currency not found: " + toCurrency);
        }

        return currencyRates.get(toCurrency.toLowerCase()).asDouble();
    }

}
