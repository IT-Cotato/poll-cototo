package org.cotato.poll.polltato.domain.poll.repository;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.team.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
	List<Poll> findAllByWorkspace(Workspace workspace);
}
