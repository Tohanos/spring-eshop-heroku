package com.kravchenko.weatherserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration("weatherIntegrationConfig")
@ImportResource("/integration/http-weather-integration.xml")
public class WeatherIntegrationConfig {
    private DirectChannel weatherChannel;

    public WeatherIntegrationConfig(DirectChannel weatherChannel) {
        this.weatherChannel = weatherChannel;
    }

    public DirectChannel getWeatherChannel() {
        return weatherChannel;
    }
}
