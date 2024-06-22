package com.volvo.weatherserver.client;

import com.volvo.weatherserver.dto.external.WeatherResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;


public interface WeatherClient {

    @GetExchange("?key={apiKey}&q={city}&days={days}")
    WeatherResponse getWeather(@PathVariable String apiKey, @PathVariable String city, @PathVariable int days);
}
