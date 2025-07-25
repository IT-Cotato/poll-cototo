package org.cotato.poll.polltato.web.controller;

import org.cotato.poll.polltato.domain.team.service.TeamUserService;
import org.cotato.poll.polltato.domain.team.service.dto.TeamUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/team-users")
@RequiredArgsConstructor
public class TeamUserController {

	private final TeamUserService teamUserService;

	@Operation(summary = "팀 사용자 정보 조회", description = "특정 사용자의 팀 정보와 관련된 정보를 조회합니다.")
	@GetMapping("/me")
	public ResponseEntity<TeamUserDto> getTeamUserInformation(
		@RequestParam("email") final String email,
		@RequestParam("sessionKey") final String sessionKey,
		@RequestParam("pollId") final Long pollId) {
		return ResponseEntity.ok(teamUserService.getTeamUserInformation(email, sessionKey, pollId));
	}
}
