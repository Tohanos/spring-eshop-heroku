package com.kravchenko.springeshop.service;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private OpenWeatherMapClient client;
    private final SimpMessagingTemplate template;

    private final String apiKey = "08df0eea27dd7d23a80083d4820ed05c";
    private final String location = "Saint Petersburg";
//    private final String url;

    public WeatherServiceImpl(SimpMessagingTemplate template) {
//        client = new OpenWeatherMapClient(apiKey); // <- конструктор вызывается раньше, чем клиент получает настройки от Cloud-Config сервера
        this.template = template;
    }

    @Override
    public String currentWeather() {
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

    public void sendWeather(String weather) {
        template.convertAndSend("/topic/weather", weather);
    }

}
