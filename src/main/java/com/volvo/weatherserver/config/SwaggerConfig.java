package com.volvo.weatherserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(Environment env) {
        String appName = env.getProperty("spring.application.name", "WeatherServer Application");
        String appDescription = "This API provides a 3-day weather forecast for the top five cities in Poland: Warsaw, Cracow, Lodz, Wroclaw, and Poznan.";
        String appVersion = "1.0.0";
        String licenseName = "Apache 2.0";
        String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0.html";

        return new OpenAPI()
                .info(new Info().title(appName)
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name(licenseName).url(licenseUrl)));
    }
}