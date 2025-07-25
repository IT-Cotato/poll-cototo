package org.cotato.poll.polltato.web.controller;

import java.util.List;

import org.cotato.poll.polltato.domain.team.service.TeamService;
import org.cotato.poll.polltato.domain.team.service.dto.TeamDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/teams")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@Operation(summary = "팀 목록 조회 API")
	@GetMapping
	public ResponseEntity<List<TeamDto>> getAllTeamsByWorkspaceId(final Long workspaceId) {
		return ResponseEntity.ok(teamService.getAllTeamsByWorkspaceId(workspaceId));
	}
}
