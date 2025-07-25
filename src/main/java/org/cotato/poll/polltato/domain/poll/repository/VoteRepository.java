package org.cotato.poll.polltato.domain.poll.repository;

import org.cotato.poll.polltato.domain.poll.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
} 