package org.cotato.poll.polltato.domain.poll.repository;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.entity.PollItemGroup;
import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.team.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollItemGroupRepository extends JpaRepository<PollItemGroup, Long> {
    List<PollItemGroup> findAllByPollAndRequiredRole(Poll poll, Role requiredRole);
} 