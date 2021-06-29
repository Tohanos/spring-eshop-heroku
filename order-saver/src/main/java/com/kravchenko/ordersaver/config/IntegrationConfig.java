package com.kravchenko.ordersaver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;

@Configuration
public class IntegrationConfig {

    @Bean("ordersChannel")
    public DirectChannel channel(){
        return new DirectChannel();
    }

}
