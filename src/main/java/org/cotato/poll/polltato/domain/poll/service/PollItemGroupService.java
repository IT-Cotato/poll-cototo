package org.cotato.poll.polltato.domain.poll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.poll.entity.PollItemGroup;
import org.cotato.poll.polltato.domain.poll.entity.PollItem;
import org.cotato.poll.polltato.domain.poll.repository.PollItemGroupRepository;
import org.cotato.poll.polltato.domain.poll.repository.PollItemRepository;
import org.cotato.poll.polltato.domain.poll.repository.PollRepository;
import org.cotato.poll.polltato.domain.poll.repository.VoteRepository;
import org.cotato.poll.polltato.domain.poll.entity.Vote;
import org.cotato.poll.polltato.domain.team.entity.User;
import org.cotato.poll.polltato.domain.team.enums.Role;
import org.cotato.poll.polltato.domain.team.repostiroy.TeamUserRepository;
import org.cotato.poll.polltato.domain.team.repostiroy.TeamRepository;
import org.cotato.poll.polltato.domain.team.repostiroy.UserRepository;
import org.cotato.poll.polltato.domain.team.entity.TeamUser;
import org.cotato.poll.polltato.domain.team.entity.Team;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PollItemGroupService {
    private final PollRepository pollRepository;
    private final PollItemGroupRepository pollItemGroupRepository;
    private final PollItemRepository pollItemRepository;
    private final VoteRepository voteRepository;
    private final TeamUserRepository teamUserRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public List<PollItemGroupWithItemsDto> getMemberPollItemGroupsWithItems(Long pollId) {
        Poll poll = pollRepository.findById(pollId)
            .orElseThrow(() -> new IllegalArgumentException("Poll not found: " + pollId));
        List<PollItemGroup> groups = pollItemGroupRepository.findAllByPollAndRequiredRole(poll, Role.MEMBER);
        return groups.stream()
            .map(group -> new PollItemGroupWithItemsDto(group, pollItemRepository.findAllByPollItemGroup(group)))
            .collect(Collectors.toList());
    }

    public List<PollItemGroupWithItemsDto> getAdminPollItemGroupsWithItems(Long pollId) {
        Poll poll = pollRepository.findById(pollId)
            .orElseThrow(() -> new IllegalArgumentException("Poll not found: " + pollId));
        List<PollItemGroup> groups = pollItemGroupRepository.findAllByPollAndRequiredRole(poll, Role.ADMIN);
        return groups.stream()
            .map(group -> new PollItemGroupWithItemsDto(group, pollItemRepository.findAllByPollItemGroup(group)))
            .collect(Collectors.toList());
    }

    @Transactional
    public void giveScoreToPollItem(Long pollItemId, Long userId, Integer score) {
        PollItem pollItem = pollItemRepository.findById(pollItemId)
            .orElseThrow(() -> new IllegalArgumentException("PollItem not found: " + pollItemId));
        PollItemGroup pollItemGroup = pollItem.getPollItemGroup();
        Poll poll = pollItemGroup.getPoll();
        Long workspaceId = poll.getWorkspace().getId();
        // 유저 정보
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        // 유저의 팀/권한
        TeamUser teamUser = teamUserRepository.findByUserIdAndTeam_WorkspaceId(userId, workspaceId)
            .orElseThrow(() -> new IllegalArgumentException("TeamUser not found for user: " + userId + " in workspace: " + workspaceId));
        if (teamUser.getRole() != Role.MEMBER) {
            throw new IllegalArgumentException("투표는 MEMBER 권한만 할 수 있습니다.");
        }
        // PollItemGroup의 팀(혹은 Poll의 팀)과 유저의 팀이 같은지 검사 (여기선 PollItemGroup이 팀을 직접 가지지 않으므로 Poll의 workspace 내 팀 비교)
        if (pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())
            && pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())
            && pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())
            && pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())) {
            // 같은 워크스페이스 내에서 팀이 같으면 투표 불가
            if (pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())
                && pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())) {
                if (pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())) {
                    if (pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())) {
                        if (pollItemGroup.getPoll().getWorkspace().getId().equals(teamUser.getTeam().getWorkspace().getId())) {
                            throw new IllegalArgumentException("자신의 팀에는 투표할 수 없습니다.");
                        }
                    }
                }
            }
        }
        Vote vote = Vote.builder()
                .pollItem(pollItem)
                .user(user)
                .score(score)
                .build();
        voteRepository.save(vote);
    }

    @Transactional
    public void adminGiveScoreToPollItem(Long pollItemId, Long userId, Integer score) {
        PollItem pollItem = pollItemRepository.findById(pollItemId)
            .orElseThrow(() -> new IllegalArgumentException("PollItem not found: " + pollItemId));
        PollItemGroup pollItemGroup = pollItem.getPollItemGroup();
        Poll poll = pollItemGroup.getPoll();
        Long workspaceId = poll.getWorkspace().getId();
        // 유저 정보
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        // 유저의 팀/권한
        TeamUser teamUser = teamUserRepository.findByUserIdAndTeam_WorkspaceId(userId, workspaceId)
            .orElseThrow(() -> new IllegalArgumentException("TeamUser not found for user: " + userId + " in workspace: " + workspaceId));
        if (teamUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("투표는 ADMIN 권한만 할 수 있습니다.");
        }
        // 어드민은 자기 팀 항목에도 투표 가능하다고 가정
        Vote vote = Vote.builder()
                .pollItem(pollItem)
                .user(user)
                .score(score)
                .build();
        voteRepository.save(vote);
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

    public static class GiveScoreRequest {
        public Long pollItemId;
        public Long userId;
        public Integer score;
    }

    public static class AdminGiveScoreRequest {
        public Long pollItemId;
        public Long userId;
        public Integer score;
    }
} 