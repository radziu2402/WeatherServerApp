package com.volvo.weatherserver.controller;


import com.volvo.weatherserver.dto.DailyForecast;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        WeatherController controller = new WeatherController(weatherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnWeatherForecast() throws Exception {
        // given
        WeatherForecastResponse weatherForecastResponse = buildWeatherForecastResponse();
        DailyForecast dailyForecast = weatherForecastResponse.getForecast().get(0);

        when(weatherService.getWeatherForecastsForTopCities()).thenReturn(List.of(weatherForecastResponse));

        // when
        mockMvc.perform(get("/api/v1/weather/forecasts/top-cities")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.[0].forecast[0].avgHumidity").value(dailyForecast.getAvgHumidity()))
                .andExpect(jsonPath("$.[0].forecast[0].avgTemperature").value(dailyForecast.getAvgTemperature()))
                .andExpect(jsonPath("$.[0].forecast[0].avgVisibility").value(dailyForecast.getAvgVisibility()))
                .andExpect(jsonPath("$.[0].forecast[0].date").value(dailyForecast.getDate()))
                .andExpect(jsonPath("$.[0].forecast[0].maxTemperature").value(dailyForecast.getMaxTemperature()))
                .andExpect(jsonPath("$.[0].forecast[0].totalPrecipitation").value(dailyForecast.getTotalPrecipitation()))
                .andExpect(jsonPath("$.[0].forecast[0].totalSnow").value(dailyForecast.getTotalSnow()))
                .andExpect(jsonPath("$.[0].forecast[0].uvIndex").value(dailyForecast.getUvIndex()));
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
