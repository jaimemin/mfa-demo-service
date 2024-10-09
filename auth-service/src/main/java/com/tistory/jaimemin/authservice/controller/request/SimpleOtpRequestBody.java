package com.tistory.jaimemin.authservice.controller.request;

import java.beans.ConstructorProperties;

import lombok.Getter;

@Getter
public class SimpleOtpRequestBody {

	private final String userId;

	private final String otpCode;

	@ConstructorProperties({"userId", "otpCode"})
	public SimpleOtpRequestBody(String userId, String otpCode) {
		this.userId = userId;
		this.otpCode = otpCode;
	}
}
