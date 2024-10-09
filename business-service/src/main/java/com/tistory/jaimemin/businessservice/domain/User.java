package com.tistory.jaimemin.businessservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

	private final String userId;

	private final String password;

	private final String otpCode;
}
