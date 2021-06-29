package com.kravchenko.springeshop.integration;

import com.kravchenko.springeshop.service.WeatherService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WeatherActivator {
    private final WeatherService weatherService;

    public WeatherActivator(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ServiceActivator(inputChannel = "weatherChannel")
    public void listenWeatherChannel (@Payload String payload, @Headers Map<String,Object> headers) {
        weatherService.getCurrentWeatherByHTTP(payload);
    }
}
