package com.example.demo;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String secret = "gQ7opnLAA3KvHX7JvkCniaBlkbAU1Qfvzdacp+sfXEFo3n6/pFBNxHFvqyr6BpWm";

	public void validateToken(String jwtToken) {
		Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(jwtToken);

	}
	
	public String generateToken(String userName, UserCredential userCreds)
	{
		HashMap<String,Object>claims = new HashMap<String, Object>();
		claims.put("role", userCreds.getRole().name());
		claims.put("name", userCreds.getName());
		System.out.println(userCreds);
		System.out.println(claims);
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName)
	{
		return Jwts.builder()
				.claims(claims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey())
				.compact();
	}

	private Key getSignKey() {
		byte[] keys = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keys);
	}
}
