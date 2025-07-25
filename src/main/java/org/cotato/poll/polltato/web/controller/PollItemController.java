package org.cotato.poll.polltato.web.controller;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.service.PollItemGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/polls/{pollId}/items")
@RequiredArgsConstructor
public class PollItemController {

	private final PollItemGroupService pollItemGroupService;

	@GetMapping("/groups/member")
	public ResponseEntity<List<PollItemGroupService.PollItemGroupWithItemsDto>> getMemberPollItemGroupsWithItems(
		@PathVariable("pollId") final Long pollId) {
		return ResponseEntity.ok(pollItemGroupService.getMemberPollItemGroupsWithItems(pollId));
	}

	@GetMapping("/groups/admin")
	public ResponseEntity<List<PollItemGroupService.PollItemGroupWithItemsDto>> getAdminPollItemGroupsWithItems(
		@PathVariable("pollId") final Long pollId) {
		return ResponseEntity.ok(pollItemGroupService.getAdminPollItemGroupsWithItems(pollId));
	}

	@PostMapping("/vote")
	public ResponseEntity<Void> votePollItem(@RequestBody PollItemGroupService.GiveScoreRequest request) {
		pollItemGroupService.giveScoreToPollItem(request.pollItemId, request.userId, request.score);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/vote/admin")
	public ResponseEntity<Void> adminVotePollItem(@RequestBody PollItemGroupService.AdminGiveScoreRequest request) {
		pollItemGroupService.adminGiveScoreToPollItem(request.pollItemId, request.userId, request.score);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/calculate-scores")
	public ResponseEntity<List<PollItemGroupService.TeamPollScoreResult>> calculateTeamPollScores(@PathVariable("pollId") Long pollId) {
		return ResponseEntity.ok(pollItemGroupService.calculateTeamPollScores(pollId));
	}

	@GetMapping("/scores")
	public ResponseEntity<List<PollItemGroupService.TeamPollScoreResult>> getTeamPollScores(@PathVariable("pollId") Long pollId) {
		return ResponseEntity.ok(pollItemGroupService.calculateTeamPollScores(pollId));
	}

}
