package com.kravchenko.springeshop.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final SimpMessagingTemplate template;

    @Override
    public void getCurrentWeatherByHTTP(String weather) {

        template.convertAndSend("/topic/weather", weather);
    }
}
