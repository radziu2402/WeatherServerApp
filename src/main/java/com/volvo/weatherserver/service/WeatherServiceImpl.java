package com.volvo.weatherserver.service;

import com.volvo.weatherserver.client.WeatherClient;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.dto.external.WeatherResponse;
import com.volvo.weatherserver.mapper.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final static List<String> CITIES = List.of("Warsaw", "Cracow", "Lodz", "Wroclaw", "Poznan");

    private static final int FORECAST_DAYS = 3;

    private final String apiKey;

    private final WeatherClient weatherClient;

    @Autowired
    public WeatherServiceImpl(HttpServiceProxyFactory httpServiceProxyFactory, @Value("${weatherapi.key}") final String apiKey) {
        this.weatherClient = httpServiceProxyFactory.createClient(WeatherClient.class);
        this.apiKey = apiKey;
    }

    @Override
    public List<WeatherForecastResponse> getWeatherForecastsForTopCities() {
        return CITIES.stream()
                .map(this::getWeatherForecastForCity)
                .toList();
    }

    private WeatherForecastResponse getWeatherForecastForCity(String city) {
        WeatherResponse weatherResponse = weatherClient.getWeather(apiKey, city, FORECAST_DAYS);
        return WeatherMapper.INSTANCE.toWeatherForecastResponse(weatherResponse);
    }
}