package com.restfulbooker.api.util;

import com.restfulbooker.api.logger.Logging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;
    private static ConfigReader configReader;

    private ConfigReader() {
        BufferedReader reader;
        String propertyFilePath = "src/main/resources/application-dev.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                Logging.error(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            Logging.error(e.getMessage());
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public static ConfigReader getInstance( ) {
        if(configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public String getBaseUrl() {
        String baseUrl = properties.getProperty("base_url");
        if(baseUrl != null) return baseUrl;
        else throw new RuntimeException("base_Url not specified in the Configuration.properties file.");
    }

    public Integer getBookingId() {
        String userId = properties.getProperty("booking_id");
        if(userId != null) return Integer.valueOf(userId);
        else throw new RuntimeException("booking_id not specified in the Configuration.properties file.");
    }

    public String getUsername() {
        String username = properties.getProperty("username");
        if(username!=null) return username;
        else throw new RuntimeException("Username not specified in the Config.properties file");
    }

    public String getPassword() {
        String password = properties.getProperty("password");
        if(password!=null) return password;
        else throw new RuntimeException("Password not specified in the Config.properties file");
    }
}
