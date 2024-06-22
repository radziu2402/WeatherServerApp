package com.volvo.weatherserver.controller;

import com.volvo.weatherserver.api.WeatherApi;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController implements WeatherApi {

    private final WeatherService weatherService;

    @Override
    public List<WeatherForecastResponse> getWeatherForecastsForTopCities() {
        return weatherService.getWeatherForecastsForTopCities();
    }
}
