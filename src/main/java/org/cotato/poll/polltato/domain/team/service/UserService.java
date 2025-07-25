package org.cotato.poll.polltato.domain.team.service;

import java.util.List;

import org.cotato.poll.polltato.domain.team.entity.Team;
import org.cotato.poll.polltato.domain.team.entity.TeamUser;
import org.cotato.poll.polltato.domain.team.entity.User;
import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.cotato.poll.polltato.domain.team.repostiroy.TeamRepository;
import org.cotato.poll.polltato.domain.team.repostiroy.TeamUserRepository;
import org.cotato.poll.polltato.domain.team.repostiroy.UserRepository;
import org.cotato.poll.polltato.domain.team.repostiroy.WorkspaceRepository;
import org.cotato.poll.polltato.domain.team.service.dto.UserDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final WorkspaceRepository workspaceRepository;

	private final TeamRepository teamRepository;

	private final TeamUserRepository teamUserRepository;

	public UserDto getUserByEmailAndKey(final String email, final String key) {
		User user = userRepository.findByEmailAndSessionKey(email, key)
			.orElseThrow(
				() -> new IllegalArgumentException("User not found with email: " + email + " and key: " + key));
		return UserDto.from(user);
	}

	public List<UserDto> getUsersByWorkspaceId(final Long workspaceId) {
		Workspace workspace = workspaceRepository.findById(workspaceId)
			.orElseThrow(() -> new IllegalArgumentException("Workspace not found with id: " + workspaceId));

		List<Team> teams = teamRepository.findAllByWorkspace(workspace);
		if (teams.isEmpty()) {
			throw new IllegalArgumentException("No teams found for workspace with id: " + workspaceId);
		}

		List<Long> userIds = teamUserRepository.findAllByTeam(teams).stream()
			.map(TeamUser::getUserId)
			.toList();

		// return userRepository.findAllById(userIds)
		// 	.stream()
		// 	.map(UserDto::from)
		// 	.toList();
		return null;
	}
}
