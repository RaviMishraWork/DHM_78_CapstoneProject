package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<String> getAllUsers()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().map(UserCredential::getEmail).toList();
	}
	public List<String> getAllUserUsers()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user -> user.getRole() == Role.USER).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllAdmins()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user -> user.getRole() == Role.ADMIN).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllStaff()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user -> user.getRole() == Role.STAFF||user.getRole()==Role.ADMIN ).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllAlertUsers()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user-> user.isAlertsEnabled()).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllAlertUserUsers()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user-> user.isAlertsEnabled()).filter(user -> user.getRole() == Role.USER).map(UserCredential::getEmail).toList();
	}
	public List <String> getAllAlertUsersByRole(Role role)
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user-> user.isAlertsEnabled()).filter(user -> user.getRole() == role).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllAlertAdmins()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user-> user.isAlertsEnabled()).filter(user -> user.getRole() == Role.ADMIN).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllAlertStaff()
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user-> user.isAlertsEnabled()).filter(user -> user.getRole() == Role.STAFF||user.getRole()==Role.ADMIN ).map(UserCredential::getEmail).toList();
	}
	public List<String> getAllUsersByRole(Role role)
	{
		List<UserCredential> userList = repository.findAll();
		return userList.stream().filter(user -> user.getRole() == role).map(UserCredential::getEmail).toList();
	}
	
	public void validateToken(String token)
	{
		jwtService.validateToken(token);
	}

	public boolean isEmailTaken(String email) {
		return repository.findByEmail(email).isPresent();
	}
	
}
