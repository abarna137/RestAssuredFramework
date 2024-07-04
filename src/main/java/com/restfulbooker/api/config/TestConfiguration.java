package com.restfulbooker.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TestConfiguration {

    @Value("${base.url}")
    private String baseURL;

    @Value("${booking.id}")
    private String bookingID;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;
}
