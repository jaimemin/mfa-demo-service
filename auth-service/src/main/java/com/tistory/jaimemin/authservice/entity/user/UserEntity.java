package com.tistory.jaimemin.authservice.entity.user;

import com.tistory.jaimemin.authservice.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String userId;

	@Column
	private String password;

	public UserEntity(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public User convertToDomain() {
		return new User(userId, password);
	}
}
