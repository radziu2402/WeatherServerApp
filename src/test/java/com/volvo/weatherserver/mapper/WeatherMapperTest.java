package com.volvo.weatherserver.mapper;

import com.volvo.weatherserver.dto.DailyForecast;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.dto.external.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherMapperTest {

    private final WeatherMapper weatherMapper = WeatherMapper.INSTANCE;

    private static Stream<Arguments> provideWeatherResponseData() {
        return Stream.of(
                Arguments.of("Warsaw", "2024-06-22", 24.0, 16.7, 20.1, 23.0, 3.04, 0.0, 9.3, 78, 6.0),
                Arguments.of("Cracow", "2024-06-22", 25.0, 17.0, 21.0, 20.0, 2.0, 0.0, 10.0, 80, 7.0),
                Arguments.of("Lodz", "2024-06-22", 26.0, 18.0, 22.0, 22.0, 1.5, 0.0, 11.0, 75, 8.0),
                Arguments.of("Wroclaw", "2024-06-22", 27.0, 19.0, 23.0, 24.0, 2.5, 0.0, 9.0, 70, 9.0),
                Arguments.of("Poznan", "2024-06-22", 28.0, 20.0, 24.0, 25.0, 3.0, 0.0, 8.0, 65, 10.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWeatherResponseData")
    void shouldMapWeatherResponseToWeatherForecastResponse(String cityName, String date, double maxTemp, double minTemp, double avgTemp,
                                                           double maxWind, double totalPrecip, double totalSnow, double avgVis, int avgHum, double uvIndex) {
        // given
        WeatherResponse weatherResponse = createMockWeatherResponse(cityName, date, maxTemp, minTemp, avgTemp, maxWind, totalPrecip, totalSnow, avgVis, avgHum, uvIndex);

        // when
        WeatherForecastResponse weatherForecastResponse = weatherMapper.toWeatherForecastResponse(weatherResponse);

        // then
        assertThat(weatherForecastResponse).isNotNull();
        assertThat(weatherForecastResponse.getCity()).isEqualTo(cityName);

        List<DailyForecast> forecasts = weatherForecastResponse.getForecast();
        assertThat(forecasts).isNotNull();
        assertThat(forecasts.size()).isEqualTo(3);

        DailyForecast dailyForecast = forecasts.get(0);
        assertThat(dailyForecast.getDate()).isEqualTo(date);
        assertThat(dailyForecast.getMaxTemperature()).isEqualTo(maxTemp);
        assertThat(dailyForecast.getMinTemperature()).isEqualTo(minTemp);
        assertThat(dailyForecast.getAvgTemperature()).isEqualTo(avgTemp);
        assertThat(dailyForecast.getMaxWindSpeed()).isEqualTo(maxWind);
        assertThat(dailyForecast.getTotalPrecipitation()).isEqualTo(totalPrecip);
        assertThat(dailyForecast.getTotalSnow()).isEqualTo(totalSnow);
        assertThat(dailyForecast.getAvgVisibility()).isEqualTo(avgVis);
        assertThat(dailyForecast.getAvgHumidity()).isEqualTo(avgHum);
        assertThat(dailyForecast.getUvIndex()).isEqualTo(uvIndex);
    }

    private static Stream<Arguments> provideForecastData() {
        return Stream.of(
                Arguments.of("2024-06-22", 24.0, 16.7, 20.1, 23.0, 3.04, 0.0, 9.3, 78, 6.0),
                Arguments.of("2024-06-23", 25.0, 17.0, 21.0, 20.0, 2.0, 0.0, 10.0, 80, 7.0),
                Arguments.of("2024-06-24", 26.0, 18.0, 22.0, 22.0, 1.5, 0.0, 11.0, 75, 8.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideForecastData")
    void shouldMapForecastToDailyForecastList(String date, double maxTemp, double minTemp, double avgTemp,
                                              double maxWind, double totalPrecip, double totalSnow, double avgVis, int avgHum, double uvIndex) {
        // Given
        Forecast forecast = createMockForecast(date, maxTemp, minTemp, avgTemp, maxWind, totalPrecip, totalSnow, avgVis, avgHum, uvIndex);

        // When
        List<DailyForecast> dailyForecasts = weatherMapper.mapForecastToDailyForecastList(forecast);

        // Then
        assertThat(dailyForecasts).isNotNull();
        assertThat(dailyForecasts.size()).isEqualTo(3);

        DailyForecast dailyForecast = dailyForecasts.get(0);
        assertThat(dailyForecast.getDate()).isEqualTo(date);
        assertThat(dailyForecast.getMaxTemperature()).isEqualTo(maxTemp);
        assertThat(dailyForecast.getMinTemperature()).isEqualTo(minTemp);
        assertThat(dailyForecast.getAvgTemperature()).isEqualTo(avgTemp);
        assertThat(dailyForecast.getMaxWindSpeed()).isEqualTo(maxWind);
        assertThat(dailyForecast.getTotalPrecipitation()).isEqualTo(totalPrecip);
        assertThat(dailyForecast.getTotalSnow()).isEqualTo(totalSnow);
        assertThat(dailyForecast.getAvgVisibility()).isEqualTo(avgVis);
        assertThat(dailyForecast.getAvgHumidity()).isEqualTo(avgHum);
        assertThat(dailyForecast.getUvIndex()).isEqualTo(uvIndex);
    }

    private WeatherResponse createMockWeatherResponse(String cityName, String date, double maxTemp, double minTemp, double avgTemp,
                                                      double maxWind, double totalPrecip, double totalSnow, double avgVis, int avgHum, double uvIndex) {
        WeatherResponse response = new WeatherResponse();

        Location location = new Location();
        location.setName(cityName);
        response.setLocation(location);

        response.setForecast(createMockForecast(date, maxTemp, minTemp, avgTemp, maxWind, totalPrecip, totalSnow, avgVis, avgHum, uvIndex));

        return response;
    }

    private Forecast createMockForecast(String date, double maxTemp, double minTemp, double avgTemp,
                                        double maxWind, double totalPrecip, double totalSnow, double avgVis, int avgHum, double uvIndex) {
        Forecast forecast = new Forecast();
        Forecastday forecastDay = new Forecastday();
        forecastDay.setDate(date);

        Day day = new Day();
        day.setMaxTemperature(maxTemp);
        day.setMinTemperature(minTemp);
        day.setAvgTemperature(avgTemp);
        day.setMaxWindSpeed(maxWind);
        day.setTotalPrecipitation(totalPrecip);
        day.setTotalSnow(totalSnow);
        day.setAvgVisibility(avgVis);
        day.setAvgHumidity(avgHum);
        day.setUvIndex(uvIndex);
        forecastDay.setDay(day);

        forecast.setForecastday(List.of(forecastDay, forecastDay, forecastDay));
        return forecast;
    }
}
