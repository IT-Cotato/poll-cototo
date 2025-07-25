package org.cotato.poll.polltato.web.controller;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.service.PollItemGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
