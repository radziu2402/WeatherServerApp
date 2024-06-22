package com.volvo.weatherserver.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SwaggerTest extends IntegrationTest {

    private static final String SWAGGER_URL = "/api/docs";

    private static final String REDIRECT_URL = "/api/swagger-ui/index.html";

    @Test
    void swaggerUrlTest() throws Exception {
        // when
        mvc.perform(get(SWAGGER_URL))
                // then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(REDIRECT_URL));
    }
}
