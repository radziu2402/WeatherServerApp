package com.volvo.weatherserver.service;

import com.volvo.weatherserver.dto.WeatherForecastResponse;

import java.util.List;

public interface WeatherService {

    List<WeatherForecastResponse> getWeatherForecastsForTopCities();
}
