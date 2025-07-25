package org.cotato.poll.polltato.domain.poll.service;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.poll.enums.PollStatus;
import org.cotato.poll.polltato.domain.poll.repository.PollRepository;
import org.cotato.poll.polltato.domain.poll.service.dto.PollDto;
import org.cotato.poll.polltato.domain.team.entity.User;
import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.cotato.poll.polltato.domain.team.repostiroy.WorkspaceRepository;
import org.cotato.poll.polltato.domain.team.service.UserService;
import org.cotato.poll.polltato.global.excepction.BusinessException;
import org.cotato.poll.polltato.global.excepction.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollService {

	private final UserService userService;
	private final MailService mailService;
	private final SessionKeyGenerator sessionKeyGenerator;

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

	@Transactional
	public void sendPollNotification(final Long workspaceId, final Long pollId) {
		Poll poll = validateAndGetPoll(workspaceId, pollId);
		List<User> users = userService.getUsersByWorkspaceId(workspaceId);

		sendEmailsToAllUsers(users, poll);
	}

	private Poll validateAndGetPoll(Long workspaceId, Long pollId) {
		Poll poll = pollRepository.findById(pollId)
			.orElseThrow(() -> new EntityNotFoundException("Poll not found with id: " + pollId));

		if (!poll.getWorkspace().getId().equals(workspaceId)) {
			throw new BusinessException(ErrorCode.NOT_IN_WORKSPACE);
		}

		return poll;
	}

	private void sendEmailsToAllUsers(List<User> users, Poll poll) {
		String workspaceName = poll.getWorkspace().getName();
		String pollTitle = poll.getTitle();
		Long pollId = poll.getId();

		log.info("Starting to send poll notification emails to {} users for poll ID: {}", users.size(), pollId);

		for (User user : users) {
			String sessionKey = sessionKeyGenerator.generateSessionKey(user.getEmail(), pollId);
			user.updateSessionKey(sessionKey);
			mailService.sendPollNotificationMail(
				user.getEmail(),
				pollTitle,
				workspaceName,
				pollId,
				sessionKey
			);
			log.info("Sent poll notification email to user: {}", user.getEmail());
		}

	}
}
