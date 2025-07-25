package org.cotato.poll.polltato.domain.team.repostiroy;

import java.util.List;
import java.util.Optional;

import org.cotato.poll.polltato.domain.team.entity.Team;
import org.cotato.poll.polltato.domain.team.entity.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
	List<TeamUser> findAllByTeamIn(List<Team> teams);

	List<TeamUser> findAllByTeam_WorkspaceId(Long workspaceId);

	Optional<TeamUser> findByUserIdAndTeam_WorkspaceId(Long userId, Long workspaceId);
}
