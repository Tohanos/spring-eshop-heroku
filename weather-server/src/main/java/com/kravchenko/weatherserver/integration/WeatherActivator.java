package com.kravchenko.weatherserver.integration;

import com.kravchenko.weatherserver.config.WeatherIntegrationConfig;
import com.kravchenko.weatherserver.service.WeatherService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class WeatherActivator {
    private final WeatherService service;
    private final WeatherIntegrationConfig config;

    public WeatherActivator(WeatherService service, WeatherIntegrationConfig config) {
        this.service = service;
        this.config = config;
    }

    public void sendWeatherChannel(String weather) {
        Message<String> message = MessageBuilder
                .withPayload(weather)
                .setHeader("Content-type", "application/json")
                .build();

        config.getWeatherChannel().send(message);

    }
}
