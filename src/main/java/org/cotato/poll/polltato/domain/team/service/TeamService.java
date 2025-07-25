package org.cotato.poll.polltato.domain.team.service;

import java.util.List;

import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.cotato.poll.polltato.domain.team.repostiroy.TeamRepository;
import org.cotato.poll.polltato.domain.team.repostiroy.WorkspaceRepository;
import org.cotato.poll.polltato.domain.team.service.dto.TeamDto;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;

	private final WorkspaceRepository workspaceRepository;

	public List<TeamDto> getAllTeamsByWorkspaceId(Long workspaceId) {
		Workspace workspace = workspaceRepository.findById(workspaceId)
			.orElseThrow(() -> new EntityNotFoundException("Workspace not found with id: " + workspaceId));
		return teamRepository.findAllByWorkspace(workspace).stream()
			.map(TeamDto::from)
			.toList();
	}
}
