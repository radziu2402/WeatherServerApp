package com.volvo.weatherserver.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Forecast {

    private List<Forecastday> forecastday;
}