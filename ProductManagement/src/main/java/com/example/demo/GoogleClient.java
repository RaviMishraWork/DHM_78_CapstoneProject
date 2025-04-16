package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@FeignClient(name="google",url="https://www.googleapis.com")
public interface GoogleClient {
	@GetMapping("/customsearch/v1")
	public String customSearch(@RequestParam String key, @RequestParam String cx, @RequestParam String q, @RequestParam String searchType);
}
