package org.ksa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the OpenAPI documentation.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Defines the OpenAPI specification for the application.
     *
     * @return a configured {@link OpenAPI} instance with metadata
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Computer and Keys API Documentation")
                        .version("1.0")
                        .description("REST Service for Computers and Keys"));
    }
}
