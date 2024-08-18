package com.example.mybudgetspring.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class DefaultCurrencyService {

    private final Properties properties = new Properties();
    private final ResourceLoader resourceLoader;

    public DefaultCurrencyService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        loadProperties();
    }

    private synchronized void loadProperties() {
        try {
            Resource resource = resourceLoader.getResource("classpath:default-currency.properties");
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDefaultCurrency() {
        return properties.getProperty("default.currency", "EUR");
    }

    public void setDefaultCurrency(String newCurrency) {
        properties.setProperty("default.currency", newCurrency);
        try {
            Resource resource = resourceLoader.getResource("classpath:default-currency.properties");
            properties.store(Files.newBufferedWriter(Paths.get(resource.getURI())), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}