package org.cotato.poll.polltato.domain.poll.service;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.repository.PollRepository;
import org.cotato.poll.polltato.domain.poll.service.dto.PollDto;
import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.cotato.poll.polltato.domain.team.repostiroy.WorkspaceRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PollService {

	private final WorkspaceRepository workspaceRepository;

	private final PollRepository pollRepository;

	public List<PollDto> getPolls(final Long workspaceId) {
		Workspace workspace = workspaceRepository.findById(workspaceId)
			.orElseThrow(() -> new EntityNotFoundException("Workspace not found with id: " + workspaceId));

		return pollRepository.findAllByWorkspace(workspace).stream()
			.map(PollDto::from)
			.toList();
	}
}
