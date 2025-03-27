package com.example.demo;

import org.apache.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;



@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory <AuthenticationFilter.Config>{
	@Autowired
	private RouteValidator validator;
	
	@Autowired
	private JWTUtil jwtutil;
	
	public static class Config {
		
	}
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	 
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if(validator.isSecure.test(exchange.getRequest())) {
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Missing Header");
				}
				
				String jwtToken = "";
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader != null && authHeader.startsWith("Bearer ")) {
					jwtToken = authHeader.substring(7);
				}
				jwtutil.validateToken(jwtToken);
			}
			return chain.filter(exchange);
		});
	}

	
	

}
