package org.cotato.poll.polltato.domain.poll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.poll.entity.PollItemGroup;
import org.cotato.poll.polltato.domain.poll.entity.PollItem;
import org.cotato.poll.polltato.domain.poll.repository.PollItemGroupRepository;
import org.cotato.poll.polltato.domain.poll.repository.PollItemRepository;
import org.cotato.poll.polltato.domain.poll.repository.PollRepository;
import org.cotato.poll.polltato.domain.team.enums.Role;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PollItemGroupService {
    private final PollRepository pollRepository;
    private final PollItemGroupRepository pollItemGroupRepository;
    private final PollItemRepository pollItemRepository;

    public List<PollItemGroupWithItemsDto> getMemberPollItemGroupsWithItems(Long pollId) {
        Poll poll = pollRepository.findById(pollId)
            .orElseThrow(() -> new IllegalArgumentException("Poll not found: " + pollId));
        List<PollItemGroup> groups = pollItemGroupRepository.findAllByPollAndRequiredRole(poll, Role.MEMBER);
        return groups.stream()
            .map(group -> new PollItemGroupWithItemsDto(group, pollItemRepository.findAllByPollItemGroup(group)))
            .collect(Collectors.toList());
    }

    public static class PollItemGroupWithItemsDto {
        public final Long groupId;
        public final String name;
        public final String description;
        public final Integer weight;
        public final List<PollItemDto> items;
        public PollItemGroupWithItemsDto(PollItemGroup group, List<PollItem> items) {
            this.groupId = group.getId();
            this.name = group.getName();
            this.description = group.getDescription();
            this.weight = group.getWeight();
            this.items = items.stream().map(PollItemDto::new).collect(Collectors.toList());
        }
    }
    public static class PollItemDto {
        public final Long itemId;
        public final String title;
        public final String description;
        public final Integer maxScore;
        public final Integer displayOrder;
        public PollItemDto(PollItem item) {
            this.itemId = item.getId();
            this.title = item.getTitle();
            this.description = item.getDescription();
            this.maxScore = item.getMaxScore();
            this.displayOrder = item.getDisplayOrder();
        }
    }
} 