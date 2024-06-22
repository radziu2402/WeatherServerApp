# WeatherServer Application

## Overview

WeatherServer is a Spring Boot application that provides a RESTful API to fetch 3-day weather forecasts for the top five cities in Poland. The application uses the [weatherapi.com](https://www.weatherapi.com/) service to retrieve weather data.

## Features

- Fetch weather forecasts for the top five cities in Poland: Warsaw, Cracow, Lodz, Wroclaw, and Poznan.
- Each forecast includes the maximum, minimum, and average temperature, wind speed, precipitation, snowfall, humidity, visibility, and UV index.
- API documentation available via Swagger/OpenAPI.

## Setup

### Prerequisites

- Java 17 or higher
- Maven

### Configuration

1. Clone the repository:
   ```sh
   git clone https://github.com/radziu2402/WeatherServerApp.git
   cd WeatherServerApp
   ```

2. Configure the API keys in `src/main/resources/application.properties`:
   ```properties
   # api key for weatherapi.com
   weatherapi.key=YOUR_API_KEY
   ```
3. Build and run the application:
   ```sh
   ./mvnw spring-boot:run
   ```

## Accessing the API

### Get Weather Forecasts for Top Cities

- **URL**: `/api/v1/weather/forecasts/top-cities`
- **Method**: `GET`
- **Response**: JSON array of weather forecasts

Example response:
```json
[
    {
        "city": "Warsaw",
        "forecast": [
            {
                "date": "2024-06-22",
                "maxTemperature": 24.0,
                "minTemperature": 16.7,
                "avgTemperature": 20.1,
                "maxWindSpeed": 23.0,
                "totalPrecipitation": 3.04,
                "totalSnow": 0.0,
                "avgVisibility": 9.3,
                "avgHumidity": 78,
                "uvIndex": 6.0
            },
            ...
        ]
    },
    ...
]
```

## API Documentation

API documentation is available via Swagger/OpenAPI. After running the application, you can access the documentation at:

```
http://localhost:8080/api/docs
```

## Testing

### Unit Tests

Unit tests have been implemented for various components of the application using JUnit 5 and Mockito. These tests cover the service and mapper layers to ensure the core logic of the application works as expected.

### Integration Tests

Integration tests have been implemented to verify the end-to-end functionality of the application, including the REST API endpoints and their interactions with external services.

#### Technologies Used for Testing

- **JUnit 5**: Used for writing and running tests.
- **Mockito**: Used for mocking dependencies and verifying interactions.
- **Spring Boot Test**: Provides support for testing Spring Boot applications, including auto-configuration and test utilities.

#### Running Tests

To run the tests, use the following Maven command:

```sh
./mvnw test
```

This command will execute all unit and integration tests and provide a summary of the test results.
