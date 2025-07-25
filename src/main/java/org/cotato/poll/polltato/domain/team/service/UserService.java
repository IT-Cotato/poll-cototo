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

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final WorkspaceRepository workspaceRepository;

	private final TeamRepository teamRepository;

	private final TeamUserRepository teamUserRepository;

	public UserDto getUserByEmail(final String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
		return UserDto.from(user);
	}

	public List<User> getUsersByWorkspaceId(final Long workspaceId) {
		Workspace workspace = workspaceRepository.findById(workspaceId)
			.orElseThrow(() -> new IllegalArgumentException("Workspace not found with id: " + workspaceId));

		List<Team> teams = teamRepository.findAllByWorkspace(workspace);
		if (teams.isEmpty()) {
			throw new IllegalArgumentException("No teams found for workspace with id: " + workspaceId);
		}

		List<Long> userIds = teamUserRepository.findAllByTeamIn(teams).stream()
			.map(TeamUser::getUserId)
			.toList();

		return userRepository.findAllById(userIds);
	}
}
