package org.cotato.poll.polltato.domain.poll.service;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.poll.enums.PollStatus;
import org.cotato.poll.polltato.domain.poll.repository.PollRepository;
import org.cotato.poll.polltato.domain.poll.service.dto.PollDto;
import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.cotato.poll.polltato.domain.team.repostiroy.WorkspaceRepository;
import org.cotato.poll.polltato.global.excepction.BusinessException;
import org.cotato.poll.polltato.global.excepction.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public PollDto getPoll(final Long workspaceId, final Long pollId) {
		Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new EntityNotFoundException(
			"Poll not found with id: " + pollId + " in workspace: " + workspaceId));

		if (!poll.getWorkspace().getId().equals(workspaceId)) {
			throw new BusinessException(ErrorCode.NOT_IN_WORKSPACE);
		}

		return PollDto.from(poll);
	}

	@Transactional
	public void updatePollStatus(final Long pollId, final PollStatus status) {
		Poll poll = pollRepository.findById(pollId)
			.orElseThrow(() -> new EntityNotFoundException("Poll not found with id: " + pollId));

		poll.updateStatus(status);
	}
}
