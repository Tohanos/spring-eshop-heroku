package com.kravchenko.springeshop.scheduled;

import com.kravchenko.springeshop.dto.WeatherDTO;
import com.kravchenko.springeshop.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherTask {

    @Autowired
    private final WeatherService service;

    private WeatherDTO weather;

    public WeatherTask(WeatherService service) {
        this.service = service;
        this.weather = new WeatherDTO(service.currentWeather());
    }

    @Scheduled(fixedRate = 600000) // Вынести в Cloud Config сервер
    public void readWeather() {
        weather = new WeatherDTO(service.currentWeather());
        System.out.println(weather);
    }

    @Scheduled(fixedRate = 30000)
    public void sendWeather() {
        service.sendWeather(weather.currentWeather());
    }

}
