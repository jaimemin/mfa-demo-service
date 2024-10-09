package com.tistory.jaimemin.authservice.controller.request;

import java.beans.ConstructorProperties;

import lombok.Getter;

@Getter
public class SimpleUserRequestBody {

	private final String userId;

	private final String password;

	@ConstructorProperties({"userId", "password"})
	public SimpleUserRequestBody(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
