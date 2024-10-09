package com.tistory.jaimemin.authservice.repository.auth;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionOperations;

import com.tistory.jaimemin.authservice.domain.User;
import com.tistory.jaimemin.authservice.entity.otp.OtpEntity;
import com.tistory.jaimemin.authservice.entity.user.UserEntity;
import com.tistory.jaimemin.authservice.repository.otp.OtpJpaRepository;
import com.tistory.jaimemin.authservice.repository.user.UserJpaRepository;
import com.tistory.jaimemin.authservice.util.OtpCodeUtil;

@ExtendWith(MockitoExtension.class)
class AuthRepositoryTest {

	@Mock
	OtpJpaRepository otpJpaRepository;

	@Mock
	UserJpaRepository userJpaRepository;

	AuthRepository authRepository;

	private String userId = "jaimemin";

	private String password = "1234";

	@BeforeEach
	public void setUp() {
		authRepository = new AuthRepository(
			otpJpaRepository,
			userJpaRepository,
			TransactionOperations.withoutTransaction(),
			TransactionOperations.withoutTransaction()
		);
	}

	@Test
	@DisplayName("동일한 사용자 ID로 사용자를 등록할 수 없다.")
	public void can_not_register_user_with_same_user_name() {
		// given
		UserEntity userEntity = new UserEntity(userId, password);
		given(userJpaRepository.findUserEntityByUserId(userId))
			.willReturn(Optional.of(userEntity));

		// when & then
		Assertions.assertThrows(RuntimeException.class, () -> {
			authRepository.createNewUser(userEntity.convertToDomain());
		});
	}

	@Test
	@DisplayName("정상적으로 사용자를 등록할 수 있다.")
	public void register_user() {
		// given
		given(userJpaRepository.findUserEntityByUserId(userId))
			.willReturn(Optional.empty());
		User user = new User(userId, password);
		given(userJpaRepository.save(any()))
			.willReturn(user.toEntity());

		// when
		User result = authRepository.createNewUser(user);

		// then
		verify(userJpaRepository, atMostOnce()).save(user.toEntity());
		Assertions.assertEquals(result.getUserId(), userId);
		Assertions.assertEquals(result.getPassword(), password);
	}

	@Test
	@DisplayName("사용자가 존재하면 OTP 값을 업데이트 한다.")
	public void upsert_otp_code_if_user_exists() {
		// given
		String otp = OtpCodeUtil.generateOtpCode();
		OtpEntity otpEntity = new OtpEntity();
		given(otpJpaRepository.findOtpEntityByUserId(userId))
			.willReturn(Optional.of(otpEntity));

		// when
		authRepository.upsertOtp(userId, otp);

		// then
		Assertions.assertEquals(otp, otpEntity.getOtpCode());
	}

	@Test
	@DisplayName("사용자가 존재하지 않으면 새로운 OTP를 생성한다.")
	public void create_new_otp_code_if_user_does_not_exists() {
		// given
		String otp = OtpCodeUtil.generateOtpCode();
		given(otpJpaRepository.findOtpEntityByUserId(userId))
			.willReturn(Optional.empty());

		// when
		authRepository.upsertOtp(userId, otp);

		// then
		verify(otpJpaRepository, atMostOnce()).save(any());
	}
}