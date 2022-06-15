package com.in28minutes.microservices.netflixzuulapigatewayserver;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

		return builder.routes().route(p -> p.path("/get")
				.filters(f -> f.addRequestHeader("MyHeader", "MyValue").addRequestParameter("MyParam", "MyValue"))
				.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange-service"))
				.route(p -> p.path("/currency-converter/**").uri("lb://currency-conversion-service"))
				.route(p -> p.path("/currency-converter-feign/**").uri("lb://currency-conversion-service"))
				.route(p -> p.path("/currency-converter-new/**")
						.filters(f -> f.rewritePath("/currency-converter-new/(?<segment>.*)",
								"/currency-converter-feign/${segment}"))
						.uri("lb://currency-conversion-service"))
				.build();
	}

}
