package com.kravchenko.springeshop.service;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private OpenWeatherMapClient client;

    private String apiKey;

    private String location;

    private String url;

    private String currentWeather;

    public WeatherServiceImpl() {
//        client = new OpenWeatherMapClient(apiKey); // <- конструктор вызывается раньше, чем клиент получает настройки от Cloud-Config сервера
    }

    @Override
    public void getCurrentWeather() {
        if (client == null) {
            client = new OpenWeatherMapClient(apiKey);
        }
        this.currentWeather = client
                .currentWeather()
                .single()
                .byCityName(location)
                .language(Language.RUSSIAN)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJSON();
    }

    public String weather() {
        return currentWeather;
    }

}
