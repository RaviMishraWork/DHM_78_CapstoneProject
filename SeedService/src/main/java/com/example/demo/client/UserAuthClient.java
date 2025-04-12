package com.example.demo.client;

import com.example.demo.data.AuthRequest;
import com.example.demo.data.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "UserAuth", url = "http://localhost:9890/security")
public interface UserAuthClient {

    @PostMapping("/register")
    @ResponseBody AuthResponse register(@RequestBody AuthRequest authRequest);
}
