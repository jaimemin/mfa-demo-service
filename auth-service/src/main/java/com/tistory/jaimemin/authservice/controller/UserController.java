package com.tistory.jaimemin.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tistory.jaimemin.authservice.controller.request.EncryptedUserRequestBody;
import com.tistory.jaimemin.authservice.domain.User;
import com.tistory.jaimemin.authservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public User createNewUser(@RequestBody EncryptedUserRequestBody requestBody) {
		return userService.createNewUser(requestBody.getUserId(), requestBody.getPassword());
	}
}
