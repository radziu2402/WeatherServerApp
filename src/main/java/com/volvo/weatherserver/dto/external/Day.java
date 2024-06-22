package com.volvo.weatherserver.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Day {

    @JsonProperty("maxtemp_c")
    private double maxTemperature;

    @JsonProperty("mintemp_c")
    private double minTemperature;

    @JsonProperty("avgtemp_c")
    private double avgTemperature;

    @JsonProperty("maxwind_kph")
    private double maxWindSpeed;

    @JsonProperty("totalprecip_mm")
    private double totalPrecipitation;

    @JsonProperty("totalsnow_cm")
    private double totalSnow;

    @JsonProperty("avgvis_km")
    private double avgVisibility;

    @JsonProperty("avghumidity")
    private int avgHumidity;

    @JsonProperty("uv")
    private double uvIndex;
}
