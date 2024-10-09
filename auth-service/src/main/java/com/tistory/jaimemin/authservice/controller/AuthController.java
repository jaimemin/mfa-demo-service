package com.tistory.jaimemin.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tistory.jaimemin.authservice.controller.request.SimpleOtpRequestBody;
import com.tistory.jaimemin.authservice.controller.request.SimpleUserRequestBody;
import com.tistory.jaimemin.authservice.service.OtpService;
import com.tistory.jaimemin.authservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

	private final OtpService otpService;

	private final UserService userService;

	@PostMapping("/users/auth")
	public String auth(@RequestBody SimpleUserRequestBody requestBody) {
		return userService.auth(requestBody.getUserId(), requestBody.getPassword());
	}

	@PostMapping("/otp/check")
	public boolean checkOtp(@RequestBody SimpleOtpRequestBody requestBody) {
		return otpService.checkOtp(requestBody.getUserId(), requestBody.getOtpCode());
	}
}
