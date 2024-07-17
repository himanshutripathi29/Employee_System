package com.Springsecurity.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRETS = "89035A2A70D0463F9535B7059E15FA6AE6739E045966358EF2F68DD6775A396D";

	// Token : Token contains header , payload , signature

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignkey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

//	public Boolean validateToken(String token, UserDetails userDetails) {
//		final String username = extractUserName(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(String userName) {

		Map<String, Object> claims = new HashMap<>();

		return createToken(claims, userName);

	}

	private String createToken(Map<String, Object> claims, String userName) {

		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 30)) // Depends on your hours
				.signWith(getSignkey(), SignatureAlgorithm.HS256).compact();

	}

	private Key getSignkey() {

		byte[] keybytes = Decoders.BASE64.decode(SECRETS);
		return Keys.hmacShaKeyFor(keybytes);
	}

}
