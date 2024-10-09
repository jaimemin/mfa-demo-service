package com.tistory.jaimemin.authservice.controller.request;

import java.beans.ConstructorProperties;

import com.tistory.jaimemin.authservice.annotation.PasswordEncryption;

import lombok.Getter;

@Getter
public class EncryptedUserRequestBody {

	private final String userId;

	@PasswordEncryption
	private String password;

	@ConstructorProperties({"userId", "password"})
	public EncryptedUserRequestBody(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
