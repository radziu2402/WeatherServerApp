package com.volvo.weatherserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${weatherapi.domain}")
    private String apiDomain;

    @Value("${weather.api.version}")
    private String apiVersion;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl(apiDomain + apiVersion + apiUrl).build();
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        return HttpServiceProxyFactory.builderFor(adapter).build();
    }
}