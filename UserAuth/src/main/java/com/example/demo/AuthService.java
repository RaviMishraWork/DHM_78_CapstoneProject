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
	
	public String saverUser(UserCredential userCredential)
	{
		userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
		repository.save(userCredential);
		return "Inserted New Record";
		
	}
	
	public String generateToken(String userName)
	{
		return jwtService.generateToken(userName);
	}
	
	
	public void validateToken(String token)
	{
		jwtService.validateToken(token);
	}
	
	
}
