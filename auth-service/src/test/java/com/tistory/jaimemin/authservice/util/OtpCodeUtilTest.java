package com.tistory.jaimemin.authservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OtpCodeUtilTest {

	@Test
	@DisplayName("6자리 숫자 값이 나와야 함")
	public void test_if_otp_code_is_valid() {
		// given & when
		String otp = OtpCodeUtil.generateOtpCode();

		// then
		// 1. 숫자 값임을 검증
		Assertions.assertTrue(otp.chars().allMatch(Character::isDigit));

		// 2. 6자리인가
		Assertions.assertEquals(6, otp.length());
	}
}