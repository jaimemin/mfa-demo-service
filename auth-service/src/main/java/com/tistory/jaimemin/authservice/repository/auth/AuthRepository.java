package com.tistory.jaimemin.authservice.repository.auth;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionOperations;

import com.tistory.jaimemin.authservice.domain.User;
import com.tistory.jaimemin.authservice.entity.otp.OtpEntity;
import com.tistory.jaimemin.authservice.entity.user.UserEntity;
import com.tistory.jaimemin.authservice.exception.InvalidAuthException;
import com.tistory.jaimemin.authservice.repository.otp.OtpJpaRepository;
import com.tistory.jaimemin.authservice.repository.user.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

	private final OtpJpaRepository otpJpaRepository;

	private final UserJpaRepository userJpaRepository;

	private final TransactionOperations readTransactionOperations;

	private final TransactionOperations writeTransactionOperations;

	public User createNewUser(User user) {
		return writeTransactionOperations.execute(status -> {
			UserEntity userEntity = userJpaRepository.findUserEntityByUserId(user.getUserId()).orElse(null);

			if (userEntity != null) {
				throw new RuntimeException(String.format("User [%s] already exists", user.getUserId()));
			}

			UserEntity savedUser = userJpaRepository.save(user.toEntity());

			return savedUser.convertToDomain();
		});
	}

	public User getUserByUserId(String userId) {
		return readTransactionOperations.execute(status ->
			userJpaRepository.findUserEntityByUserId(userId)
				.orElseThrow(InvalidAuthException::new)
				.convertToDomain());
	}

	public String getOtp(String userId) {
		return readTransactionOperations.execute(status -> otpJpaRepository.findOtpEntityByUserId(userId)
			.orElseThrow(() -> new RuntimeException(String.format("User [%s] doesn't exist", userId)))
			.getOtpCode());
	}

	public void upsertOtp(String userId, String newOtp) {
		writeTransactionOperations.executeWithoutResult(status -> {
			OtpEntity otpEntity = otpJpaRepository.findOtpEntityByUserId(userId).orElse(null);

			if (otpEntity == null) {
				otpJpaRepository.save(new OtpEntity(userId, newOtp));
			} else {
				otpEntity.renewOtp(newOtp);
			}
		});
	}
}
