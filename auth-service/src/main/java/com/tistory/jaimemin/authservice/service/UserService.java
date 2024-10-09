package com.tistory.jaimemin.authservice.service;

import org.springframework.stereotype.Service;

import com.tistory.jaimemin.authservice.domain.User;
import com.tistory.jaimemin.authservice.repository.auth.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final OtpService otpService;

	private final EncryptService encryptService;

	private final AuthRepository authRepository;

	public User createNewUser(String userId, String password) {
		return authRepository.createNewUser(new User(userId, password));
	}

	public String auth(String userId, String password) {
		User user = authRepository.getUserByUserId(userId);

		if (encryptService.matches(password, user.getPassword())) {
			return otpService.renewOtp(userId);
		}

		throw new RuntimeException();
	}
}
