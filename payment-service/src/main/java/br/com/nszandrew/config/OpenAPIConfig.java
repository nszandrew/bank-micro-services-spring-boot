package br.com.nszandrew.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI CustomerServiceoAPI() {
        return new OpenAPI()
                .info(new Info().title("Payment Service API")
                        .description("This is RestAPI for your Payments with cards")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")));
    }
}