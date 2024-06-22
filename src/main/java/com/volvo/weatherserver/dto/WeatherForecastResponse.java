package com.volvo.weatherserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WeatherForecastResponse {

    private String city;

    private List<DailyForecast> forecast;
}