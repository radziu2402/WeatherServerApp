package com.volvo.weatherserver.api;

import com.volvo.weatherserver.dto.WeatherForecastResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/weather")
@Tag(name = "Weather Forecasts", description = "API for accessing weather forecasts for the top five cities in Poland: Warsaw, Kraków, Łódź, Wrocław, and Poznań.")
public interface WeatherApi {

    @Operation(
            summary = "Retrieve weather forecasts",
            description = "Provides a 3-day weather forecast for the top five cities in Poland. Each forecast includes the daily maximum, minimum, and average temperature, wind speed, precipitation, snowfall, humidity, visibility, and UV index.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved weather forecasts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherForecastResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid")
            }
    )
    @GetMapping("/forecasts/top-cities")
    List<WeatherForecastResponse> getWeatherForecastsForTopCities();
}
