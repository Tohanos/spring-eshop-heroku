package com.kravchenko.springeshop.dto;

public class WeatherDTO {
    private final String weather;

    public WeatherDTO(String weather) {
        this.weather = weather;
    }

    public String currentWeather() {
        return weather;
    }
}
