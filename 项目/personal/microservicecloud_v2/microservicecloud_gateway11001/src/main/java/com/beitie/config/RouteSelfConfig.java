package com.beitie.config;

import com.beitie.filters.RequestTimeFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/25
 */
@Configuration
public class RouteSelfConfig {
    @Bean
    public RouteLocator buildRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("baiduSelfConfig",r -> r.path("/{guonei}").uri("http://news.baidu.com")).build();
        return routes.build();
    }
//    @Bean
//    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/{para}/**")
//                        .filters(f -> f.filter(new RequestTimeFilter())
//                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
//                        .uri("http://192.168.220.1:11000/product")
//                        .order(0)
//                        .id("customer_filter_router")
//                )
//                .build();
//    }



}
