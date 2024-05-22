package com.meer.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
		private final Key key;
	
		// secretKey 값을 가지고서 key 생성
	public JwtUtil(@Value("${jwt-secretkey}") String secretkey) {
		this.key = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
		
	}
	
	public String createAccessToken(String userId) {
		return createToken(userId);
	}

	private String createToken(String userId) {
		
		return Jwts.builder()
				.header().add("typ", "JWT")
				.and()						
				.claim("userId", userId)	
				.subject(userId)
				.issuedAt(new Date(System.currentTimeMillis()))
				// token의 유효시간은 1시간
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(key)
				.compact();
	}

	
	// userId를 반환하게 되어있음.
	public String validate(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getBody();		
			return claims.getSubject();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	public static void main(String[] args) {
		JwtUtil ju = new JwtUtil("asdfasdfasdfasdfasdfasdfasdfasdfzxcvkamwevmkasvn");
		String token = ju.createAccessToken("ssafy");
		System.out.println(ju.validate(token));
	}
}
