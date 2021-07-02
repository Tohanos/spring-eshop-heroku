package com.kravchenko.weatherserver.scheduled;

import com.kravchenko.weatherserver.integration.WeatherActivator;
import com.kravchenko.weatherserver.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private final WeatherActivator activator;
    private final WeatherService service;

    public ScheduledTask(WeatherActivator activator, WeatherService service) {
        this.activator = activator;
        this.service = service;
    }

    @Scheduled(fixedRate = 300000) // Вынести в Cloud Config сервер
    public void run() {
        activator.sendWeatherChannel(service.getCurrentWeather());
    }
}
