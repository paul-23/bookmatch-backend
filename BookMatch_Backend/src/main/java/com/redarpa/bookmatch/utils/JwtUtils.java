/**
 * 
 */
package com.redarpa.bookmatch.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.redarpa.bookmatch.service.UserDetailsImpl;

import io.jsonwebtoken.*;

/**
 * @author Marc
 *
 */
@Component
public class JwtUtils {

	// Get logger
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	// Get jwt secret from application.properties
	@Value("${com.redarpa.bookmatch.jwtSecret}")
	private String jwtSecret;

	// Get expiration time from application.properties
	@Value("${com.redarpa.bookmatch.jwtExpirationMs}")
	private int jwtExpirationMs;

	// Function to generate JWT Tokens
	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// Function to get username from token
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	// Function to validate token
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
