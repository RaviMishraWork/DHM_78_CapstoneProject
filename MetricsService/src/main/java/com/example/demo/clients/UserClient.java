package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "UserAuth",
        url = "http://localhost:9890",
        path = "/security/"
)
public interface UserClient {
    @GetMapping("/users")
    List<String> getAllUsers();
    @GetMapping("/admins")
    List<String> getAllAdmins();
    @GetMapping("/staff")
    List<String> getAllStaff();
}
