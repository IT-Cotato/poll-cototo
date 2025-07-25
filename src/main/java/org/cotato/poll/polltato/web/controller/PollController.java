package org.cotato.poll.polltato.web.controller;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.service.PollService;
import org.cotato.poll.polltato.domain.poll.service.dto.PollDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Poll", description = "투표 관련 API")
@RestController
@RequestMapping("/api/workspaces/{workspaceId}/polls")
@RequiredArgsConstructor
public class PollController {

	private final PollService pollService;

	@Operation(summary = "워크스페이스 내 존재하는 투표 조회", description = "워크스페이스 내 존재하는 투표들을 조회합니다.")
	@GetMapping
	public ResponseEntity<List<PollDto>> getPolls(@PathVariable("workspaceId") final Long workspaceId) {
		return ResponseEntity.ok(pollService.getPolls(workspaceId));
	}

	@Operation(summary = "워크스페이스 내 특정 투표 조회", description = "워크스페이스 내 특정 투표를 조회합니다.")
	@GetMapping("/{pollId}")
	public ResponseEntity<PollDto> getPoll(@PathVariable("workspaceId") final Long workspaceId,
		@PathVariable("pollId") final Long pollId) {
		return ResponseEntity.ok(pollService.getPoll(workspaceId, pollId));
	}
}
