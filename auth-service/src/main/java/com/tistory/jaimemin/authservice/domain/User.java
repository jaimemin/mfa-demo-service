package com.tistory.jaimemin.authservice.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tistory.jaimemin.authservice.entity.user.UserEntity;

import lombok.Getter;

@Getter
public class User implements UserDetails {

	private final String userId;

	private final String password;

	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

	public UserEntity toEntity() {
		return new UserEntity(this.userId, this.password);
	}
}
