package com.volvo.weatherserver.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Forecastday {

   private String date;

   private Day day;
}