package org.cotato.poll.polltato.web.controller;

import org.cotato.poll.polltato.domain.team.service.TeamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/teams")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;
}
