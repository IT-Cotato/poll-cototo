package org.cotato.poll.polltato.domain.team.service;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.poll.repository.PollRepository;
import org.cotato.poll.polltato.domain.team.entity.TeamUser;
import org.cotato.poll.polltato.domain.team.repostiroy.TeamUserRepository;
import org.cotato.poll.polltato.domain.team.service.dto.TeamUserDto;
import org.cotato.poll.polltato.domain.team.service.dto.UserDto;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamUserService {

	private final TeamUserRepository teamUserRepository;

	private final PollRepository pollRepository;

	private final UserService userService;

	public TeamUserDto getTeamUserInformation(final String email, final String sessionKey, final Long pollId) {
		UserDto userDto = userService.getUserByEmailAndKey(email, sessionKey);

		Poll poll = pollRepository.findById(pollId)
			.orElseThrow(() -> new EntityNotFoundException("Poll not found with id: " + pollId));

		TeamUser teamUser = teamUserRepository.findByUserIdAndTeam_WorkspaceId(userDto.getId(),
				poll.getWorkspace().getId())
			.orElseThrow(() -> new EntityNotFoundException(
				"TeamUser not found for user: " + userDto.getId() + " in workspace: " + poll.getWorkspace().getId()));

		return TeamUserDto.from(teamUser);
	}
}
