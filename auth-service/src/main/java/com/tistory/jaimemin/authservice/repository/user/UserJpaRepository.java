package com.tistory.jaimemin.authservice.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tistory.jaimemin.authservice.entity.user.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findUserEntityByUserId(String userId);
}
