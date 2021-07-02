package com.kravchenko.weatherserver.service;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private OpenWeatherMapClient client;

    @Value("${config.api_key}")
    private String apiKey;

    @Value("${config.location}")
    private String location;

    @Value("${config.url}")
    private String url;

    public WeatherService() {
//        client = new OpenWeatherMapClient(apiKey); // <- конструктор вызывается раньше, чем клиент получает настройки от Cloud-Config сервера
    }

    public String getCurrentWeather() {
        if (client == null) {
            client = new OpenWeatherMapClient(apiKey);
        }
        return client
                .currentWeather()
                .single()
                .byCityName(location)
                .language(Language.RUSSIAN)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJSON();
    }
}
