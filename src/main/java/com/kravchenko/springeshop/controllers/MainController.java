package com.kravchenko.springeshop.controllers;

import com.kravchenko.springeshop.service.ProductService;
import com.kravchenko.springeshop.service.WeatherService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {
    private final WeatherService weatherService;

    public MainController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping({"", "/"})
    public String index() {
        weatherService.sendWeather(weatherService.currentWeather());
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
