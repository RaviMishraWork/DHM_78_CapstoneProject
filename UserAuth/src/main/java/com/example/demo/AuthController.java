package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager manager;

	@PostMapping("/register")
	public String addUser(@RequestBody UserCredential userCredential) {
		return service.saverUser(userCredential);
	}

	@PostMapping("/login")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		Authentication auth = manager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
				);

		if (auth.isAuthenticated()) {
			return service.generateToken(authRequest.getUsername());
		} else {
			throw new RuntimeException("Invalid");
		}
	}

	@GetMapping("/validate")
	public String validToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return "Valid Token";
	}

}
