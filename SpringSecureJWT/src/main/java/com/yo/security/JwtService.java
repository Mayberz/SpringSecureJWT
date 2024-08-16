package com.yo.security;

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

	private static final String SECRECT_KEY = "9b50637b9b31e45ec5fc917f3054c854d31e3e370c9fade3dada470dd92942de";

	public String extractUsername(String token) {

		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {

		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);

	}
	
	public String genToken(String username) {
		return createToken(new HashMap<>(),username);
	}
	
	public String createToken(Map<String, Object> extraclaims,String username ) {
		return Jwts.builder()
				.setClaims(extraclaims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *24))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	public boolean isTokenvalid(String token,UserDetails userDetails) {
		final String username=extractUsername(token);
		return(username.equals(userDetails.getUsername())) && !isTokenExpired(token); 
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRECT_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
