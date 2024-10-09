package com.tistory.jaimemin.authservice.service;

import org.springframework.stereotype.Service;

import com.tistory.jaimemin.authservice.repository.auth.AuthRepository;
import com.tistory.jaimemin.authservice.util.OtpCodeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {

	private final AuthRepository authRepository;

	public boolean checkOtp(String userId, String sourceOtp) {
		String targetOtp = authRepository.getOtp(userId);

		return targetOtp.equals(sourceOtp);
	}

	public String renewOtp(String userId) {
		String newOtp = OtpCodeUtil.generateOtpCode();
		authRepository.upsertOtp(userId, newOtp);

		return newOtp;
	}
}
