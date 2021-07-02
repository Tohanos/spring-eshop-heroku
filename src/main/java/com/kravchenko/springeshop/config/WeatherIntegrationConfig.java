package com.kravchenko.springeshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;

@Configuration
public class WeatherIntegrationConfig {

    @Bean("weatherChannel")
    public DirectChannel channel(){
        return new DirectChannel();
    }
}
