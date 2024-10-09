package com.tistory.jaimemin.businessservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tistory.jaimemin.businessservice.security.InitAuthFilter;
import com.tistory.jaimemin.businessservice.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	private final InitAuthFilter initAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(initAuthFilter, BasicAuthenticationFilter.class);
		http.addFilterAfter(jwtAuthFilter, BasicAuthenticationFilter.class);
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeRequests(c -> c.anyRequest().authenticated());

		return http.build();
	}
}
