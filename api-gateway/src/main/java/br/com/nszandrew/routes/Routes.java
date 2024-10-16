package br.com.nszandrew.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    //Customer Service
    @Bean
    public RouterFunction<ServerResponse> customerServiceRoute() {
        return route("customer_service")
                .route(RequestPredicates.path("/api/customer/**"), HandlerFunctions.http("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("customerServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> customerServiceSwaggerRoute() {
        return route("customer_service_swagger")
                .route(RequestPredicates.path("aggregate/customer-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("customerService-SwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    //Account Service
    @Bean
    public RouterFunction<ServerResponse> accountServiceRoute() {
        return route("account_service")
                .route(RequestPredicates.path("/api/account/**"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("accountServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> accountServiceSwaggerRoute() {
        return route("account_service_swagger")
                .route(RequestPredicates.path("/aggregate/account-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("accountService-SwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    //Transaction Service
    @Bean
    public RouterFunction<ServerResponse> transactionServiceRoute() {
        return route("transaction_service")
                .route(RequestPredicates.path("/api/transaction/**"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("transactionServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> transactionServiceSwaggerRoute() {
        return route("transaction_service_swagger")
                .route(RequestPredicates.path("/aggregate/transaction-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("transactionService-SwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    //Credit Service
    @Bean
    public RouterFunction<ServerResponse> creditServiceRoute() {
        return route("credit_service")
                .route(RequestPredicates.path("/api/credit/**"), HandlerFunctions.http("http://localhost:8083"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("creditServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> creditServiceSwaggerRoute() {
        return route("credit_service_swagger")
                .route(RequestPredicates.path("/aggregate/credit-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8083"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("creditService-SwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    //Payment Service
    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoute() {
        return route("payment_service")
                .route(RequestPredicates.path("/api/payment/**"), HandlerFunctions.http("http://localhost:8084"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("paymentServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceSwaggerRoute() {
        return route("payment_service_swagger")
                .route(RequestPredicates.path("/aggregate/payment-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8084"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("paymentService-SwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }


    //Card Service
    @Bean
    public RouterFunction<ServerResponse> cardServiceRoute() {
        return route("card_service")
                .route(RequestPredicates.path("/api/card/**"), HandlerFunctions.http("http://localhost:8085"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("cardServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cardServiceSwaggerRoute() {
        return route("card_service_swagger")
                .route(RequestPredicates.path("/aggregate/card-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8085"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("cardService-SwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Service unavailable, please try again later"))
                .build();
    }
}
