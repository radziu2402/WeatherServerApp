package com.volvo.weatherserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DailyForecast {
    private String date;
    private double maxTemperature;
    private double minTemperature;
    private double avgTemperature;
    private double maxWindSpeed;
    private double totalPrecipitation;
    private double totalSnow;
    private double avgVisibility;
    private int avgHumidity;
    private double uvIndex;
}
