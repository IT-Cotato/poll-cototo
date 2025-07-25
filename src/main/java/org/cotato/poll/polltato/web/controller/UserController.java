package org.cotato.poll.polltato.web.controller;

import org.cotato.poll.polltato.domain.team.service.UserService;
import org.cotato.poll.polltato.domain.team.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "현재 사용자 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser(@RequestParam("email") final String email,
		@RequestParam("sessionKey") final String sessionKey) {
		return ResponseEntity.ok(userService.getUserByEmail(email, sessionKey));
	}

}
