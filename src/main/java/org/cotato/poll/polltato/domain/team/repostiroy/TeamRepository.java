package org.cotato.poll.polltato.domain.team.repostiroy;

import java.util.List;

import org.cotato.poll.polltato.domain.team.entity.Team;
import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

	List<Team> findAllByWorkspace(Workspace workspace);

	List<Team> findAllByWorkspaceId(Long workspaceId);
}
