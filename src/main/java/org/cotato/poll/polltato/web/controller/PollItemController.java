package org.cotato.poll.polltato.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/polls/{pollId}/items")
@RequiredArgsConstructor
public class PollItemController {

	// private final PollItemService pollItemService;
	//
	// @GetMapping
	// public ResponseEntity<?> getPollItems(@PathVariable("pollId") final Long pollId) {
	// 	return ResponseEntity.ok(pollItemService.getPollItems(pollId));
	// }

}
