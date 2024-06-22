package com.volvo.weatherserver.integration;

import com.volvo.weatherserver.dto.DailyForecast;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherServerIntegrationTest extends IntegrationTest {

    private static final String url = "/api/v1/weather/forecasts/top-cities";

    @MockBean
    private WeatherService weatherService;

    @Test
    void testWeatherForecastEndpoint() throws Exception {
        // given
        List<WeatherForecastResponse> forecastResponses = List.of(buildWeatherForecastResponse());

        when(weatherService.getWeatherForecastsForTopCities()).thenReturn(forecastResponses);

        // when
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city").value("New York City"))
                .andExpect(jsonPath("$[0].forecast", hasSize(1)))
                .andExpect(jsonPath("$[0].forecast[0].avgHumidity").value(2))
                .andExpect(jsonPath("$[0].forecast[0].avgTemperature").value(24.0))
                .andExpect(jsonPath("$[0].forecast[0].avgVisibility").value(3.03))
                .andExpect(jsonPath("$[0].forecast[0].date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].forecast[0].maxTemperature").value(34.0))
                .andExpect(jsonPath("$[0].forecast[0].totalPrecipitation").value(20.5))
                .andExpect(jsonPath("$[0].forecast[0].totalSnow").value(0.0))
                .andExpect(jsonPath("$[0].forecast[0].uvIndex").value(2.0));
    }

    private WeatherForecastResponse buildWeatherForecastResponse() {
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setCity("New York City");

        DailyForecast dailyForecast = new DailyForecast();
        dailyForecast.setAvgHumidity(2);
        dailyForecast.setAvgTemperature(24.0);
        dailyForecast.setAvgVisibility(3.03);
        dailyForecast.setDate(LocalDate.now().toString());
        dailyForecast.setMaxTemperature(34.0);
        dailyForecast.setTotalPrecipitation(20.5);
        dailyForecast.setTotalSnow(0.0);
        dailyForecast.setUvIndex(2.0);

        weatherForecastResponse.setForecast(List.of(dailyForecast));
        return weatherForecastResponse;
    }
}
