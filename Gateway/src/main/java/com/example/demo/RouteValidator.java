package com.example.demo;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	List<String> openEndPoints =
			List.of("/auth/register","/auth/token");
	
	public Predicate<ServerHttpRequest> isSecure = request-> 
			openEndPoints.stream()
			.noneMatch(uri ->
			request.getURI().getPath().contains(uri));

}
