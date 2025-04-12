package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserCredentialRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	public AuthResponse saverUser(UserCredential userCredential)
	{
		userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
		repository.save(userCredential);
		return new AuthResponse(true, "Inserted New Record");
		
	}
	
	public AuthResponse generateToken(String userName)
	{
		UserCredential userCreds = repository.findByEmail(userName).orElse(null);
		return new AuthResponse(true, jwtService.generateToken(userName, userCreds));
	}
	
	
	public void validateToken(String token)
	{
		jwtService.validateToken(token);
	}
	
	
}
