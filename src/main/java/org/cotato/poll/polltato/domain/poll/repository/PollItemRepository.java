package org.cotato.poll.polltato.domain.poll.repository;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.entity.PollItem;
import org.cotato.poll.polltato.domain.poll.entity.PollItemGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollItemRepository extends JpaRepository<PollItem, Long> {
    List<PollItem> findAllByPollItemGroup(PollItemGroup pollItemGroup);
} 