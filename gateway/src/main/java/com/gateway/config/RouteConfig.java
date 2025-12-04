package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder
                .routes()
                .route("auth",r->r.path("/api/auth/**")
                        .uri("lb://auth"))
                .route("order",r->r.path("/api/order/**")
                        .uri("lb://order"))
                .route("cart",r->r.path("/api/cart/**")
                        .uri("lb://cart"))
                .route("inventory",r->r.path("/api/inventory/**")
                        .uri("lb://inventory"))
                .route("product",r->r.path("/api/product/**")
                        .uri("lb://product"))
                .build();
    }

}
