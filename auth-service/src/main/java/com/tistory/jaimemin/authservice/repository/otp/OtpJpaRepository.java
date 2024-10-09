package com.tistory.jaimemin.authservice.repository.otp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tistory.jaimemin.authservice.entity.otp.OtpEntity;

public interface OtpJpaRepository extends JpaRepository<OtpEntity, Integer> {

	Optional<OtpEntity> findOtpEntityByUserId(String userId);
}
