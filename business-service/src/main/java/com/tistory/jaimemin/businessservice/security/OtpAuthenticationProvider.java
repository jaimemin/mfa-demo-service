package com.tistory.jaimemin.businessservice.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.tistory.jaimemin.businessservice.delegator.AuthenticationDelegator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OtpAuthenticationProvider implements AuthenticationProvider {

	private final AuthenticationDelegator delegator;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String otpCode = String.valueOf(authentication.getCredentials());

		boolean result = delegator.restOtp(userId, otpCode);

		if (result) {
			return new OtpAuthentication(userId, otpCode);
		}

		throw new BadCredentialsException("Invalid OTP");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OtpAuthentication.class.isAssignableFrom(authentication);
	}
}
