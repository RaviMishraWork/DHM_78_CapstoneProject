package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class AuthController {

	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager manager;

	@PostMapping("/register")
	public AuthResponse addUser(@RequestBody UserCredential userCredential) {
		return service.saverUser(userCredential);
	}

	@PostMapping("/login")
	public AuthResponse generateToken(@RequestBody AuthRequest authRequest) {
		Authentication auth = manager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
				);

		if (auth.isAuthenticated()) {
			return service.generateToken(authRequest.getUsername());
		} else {
			throw new RuntimeException("Invalid");
		}
	}
	@PostMapping("/isemailtaken")
	public Map<String,String> isEmailTaken(@RequestBody String email) {
		boolean data =  service.isEmailTaken(email);
		HashMap<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("successful","true");
		map.put("data", String.valueOf(data));
		if(data) {
			map.put("message", "Email is already taken");
		} else {
			map.put("message", "Email is available");
		}
		return map;
	}

	@GetMapping("/validate")
	public String validToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return "Valid Token";
	}
	@GetMapping("/users")
	public List<String> getAllUsers() {
		return service.getAllUsers();
	}
	@GetMapping("/userusers")
	public List<String> getAllUserUsers() {
		return service.getAllUserUsers();
	}

	@GetMapping("/admins")
	public List<String> getAllAdmins() {
		return service.getAllAdmins();
	}

	@GetMapping("/staff")
	public List<String> getAllStaff() {
		return service.getAllStaff();
	}
	@GetMapping("/alerts/userusers")
	public List<String> getAllAlertUserUsers() {
		return service.getAllAlertUserUsers();
	}
	@GetMapping("/alerts/users")
	public List<String> getAllAlertUsers() {
		return service.getAllAlertUsers();
	}

	@GetMapping("/alerts/admins")
	public List<String> getAllAlertAdmins() {
		return service.getAllAlertAdmins();
	}

	@GetMapping("/alerts/staff")
	public List<String> getAllAlertStaff() {
		return service.getAllAlertStaff();
	}


}
