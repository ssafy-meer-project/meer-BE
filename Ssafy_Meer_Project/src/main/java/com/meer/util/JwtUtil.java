package com.meer.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final SecretKey secretKey;
	
	public JwtUtil(@Value("${jwt-key}") String key) {
		this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));		
	}

//	private String key = "meerProject_jwtkey_jaehyun_GreatJob";
//	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

	// 다양한 데이터를 Map으로 받아서 처리를 할수 도 있지만,
	// 심플하게 ID만 받아서 토큰을 만들어보자~~
	public String createToken(String userId) {
		return Jwts.builder()
				.header().add("typ", "JWT")
				.and()
				.claim("userId", userId)	
				.subject(userId)
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(secretKey)
				.compact();
	}

	
	// userId를 반환하게 되어있음.
	public String validate(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getBody();			
			return claims.getSubject();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
//	public static void main(String[] args) {
//		JwtUtil a = new JwtUtil("Asdfasdkflkkmv;lkmv;alskdmf;lkawmeflkmsdvlkmasdjanflkjwenflkjw");
//		System.out.println(a);
//		
//		String token = a.createToken("ssafy123");
//		
//		String b = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(("Asdfasdkflkkmv;lkmv;alskdmf;lkawmeflkmsdvlkmasdjanflkjwenflkjw").
//				getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(token).getBody().getSubject();
//		System.out.println(token);
//		System.out.println(b);
//	}

}
