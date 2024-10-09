package com.tistory.jaimemin.businessservice.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitAuthFilter extends OncePerRequestFilter {

	private final OtpAuthenticationProvider otpAuthenticationProvider;

	private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

	@Value("${jwt.signing-key}")
	private String jwtKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		String otpCode = request.getHeader("otpCode");

		if (StringUtils.isBlank(otpCode)) {
			UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(username, password);
			usernamePasswordAuthenticationProvider.authenticate(authentication);
		} else {
			Authentication authentication = new OtpAuthentication(username, otpCode);
			otpAuthenticationProvider.authenticate(authentication);

			SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
			String jwt = Jwts.builder()
				.claim("username", username)
				.signWith(secretKey)
				.compact();

			response.setHeader("Authorization", jwt);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/login");
	}
}
