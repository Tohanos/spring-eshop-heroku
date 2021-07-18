package com.kravchenko.springeshop.service;

public interface WeatherService {
    String currentWeather();
    void sendWeather(String weather);
}
