package com.volvo.weatherserver.mapper;

import com.volvo.weatherserver.dto.DailyForecast;
import com.volvo.weatherserver.dto.WeatherForecastResponse;
import com.volvo.weatherserver.dto.external.Forecast;
import com.volvo.weatherserver.dto.external.Forecastday;
import com.volvo.weatherserver.dto.external.WeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mapping(target = "city", source = "location.name")
    @Mapping(target = "forecast", source = "forecast", qualifiedByName = "mapForecastToDailyForecastList")
    WeatherForecastResponse toWeatherForecastResponse(WeatherResponse weatherResponse);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "maxTemperature", source = "day.maxTemperature")
    @Mapping(target = "minTemperature", source = "day.minTemperature")
    @Mapping(target = "avgTemperature", source = "day.avgTemperature")
    @Mapping(target = "maxWindSpeed", source = "day.maxWindSpeed")
    @Mapping(target = "totalPrecipitation", source = "day.totalPrecipitation")
    @Mapping(target = "totalSnow", source = "day.totalSnow")
    @Mapping(target = "avgVisibility", source = "day.avgVisibility")
    @Mapping(target = "avgHumidity", source = "day.avgHumidity")
    @Mapping(target = "uvIndex", source = "day.uvIndex")
    DailyForecast toDailyForecast(Forecastday forecastDay);

    @Named("mapForecastToDailyForecastList")
    default List<DailyForecast> mapForecastToDailyForecastList(Forecast forecast) {
        return forecast.getForecastday().stream()
                .map(this::toDailyForecast)
                .collect(Collectors.toList());
    }
}
