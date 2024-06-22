package com.volvo.weatherserver.service;

import com.volvo.weatherserver.client.WeatherClient;
import com.volvo.weatherserver.dto.DailyForecast;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.dto.external.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    private final static int FORECAST_DAYS = 3;

    private final static String API_KEY = "apiKey";

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private HttpServiceProxyFactory httpServiceProxyFactory;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        when(httpServiceProxyFactory.createClient(WeatherClient.class)).thenReturn(weatherClient);
        this.weatherService = new WeatherServiceImpl(httpServiceProxyFactory, API_KEY);
    }

    @Test
    void testGetWeatherForecastsForTopCities() {
        // given
        WeatherResponse mockResponse = createMockWeatherResponse("Warsaw");

        when(weatherClient.getWeather(eq(API_KEY), eq("Warsaw"), eq(FORECAST_DAYS))).thenReturn(mockResponse);
        when(weatherClient.getWeather(eq(API_KEY), eq("Cracow"), eq(FORECAST_DAYS))).thenReturn(mockResponse);
        when(weatherClient.getWeather(eq(API_KEY), eq("Lodz"), eq(FORECAST_DAYS))).thenReturn(mockResponse);
        when(weatherClient.getWeather(eq(API_KEY), eq("Wroclaw"), eq(FORECAST_DAYS))).thenReturn(mockResponse);
        when(weatherClient.getWeather(eq(API_KEY), eq("Poznan"), eq(FORECAST_DAYS))).thenReturn(mockResponse);

        // when
        List<WeatherForecastResponse> forecasts = weatherService.getWeatherForecastsForTopCities();

        // then
        assertThat(forecasts).isNotNull();
        assertThat(forecasts.size()).isEqualTo(5);

        WeatherForecastResponse forecast = forecasts.get(0);
        assertThat(forecast.getCity()).isEqualTo("Warsaw");
        assertThat(forecast.getForecast()).isNotNull();
        assertThat(forecast.getForecast().size()).isEqualTo(FORECAST_DAYS);

        DailyForecast dailyForecast = forecast.getForecast().get(0);
        assertThat(dailyForecast.getDate()).isEqualTo("2024-06-22");
        assertThat(dailyForecast.getMaxTemperature()).isEqualTo(24.0);
        assertThat(dailyForecast.getMinTemperature()).isEqualTo(16.7);
        assertThat(dailyForecast.getAvgTemperature()).isEqualTo(20.1);
        assertThat(dailyForecast.getMaxWindSpeed()).isEqualTo(23.0);
        assertThat(dailyForecast.getTotalPrecipitation()).isEqualTo(3.04);
        assertThat(dailyForecast.getTotalSnow()).isEqualTo(0.0);
        assertThat(dailyForecast.getAvgVisibility()).isEqualTo(9.3);
        assertThat(dailyForecast.getAvgHumidity()).isEqualTo(78);
        assertThat(dailyForecast.getUvIndex()).isEqualTo(6.0);
    }

    private WeatherResponse createMockWeatherResponse(String cityName) {
        WeatherResponse response = new WeatherResponse();

        Location location = new Location();
        location.setName(cityName);
        response.setLocation(location);

        Forecast forecast = new Forecast();
        Forecastday forecastDay = new Forecastday();
        forecastDay.setDate("2024-06-22");

        Day day = new Day();
        day.setMaxTemperature(24.0);
        day.setMinTemperature(16.7);
        day.setAvgTemperature(20.1);
        day.setMaxWindSpeed(23.0);
        day.setTotalPrecipitation(3.04);
        day.setTotalSnow(0.0);
        day.setAvgVisibility(9.3);
        day.setAvgHumidity(78);
        day.setUvIndex(6.0);
        forecastDay.setDay(day);

        forecast.setForecastday(List.of(forecastDay, forecastDay, forecastDay));
        response.setForecast(forecast);

        return response;
    }
}
