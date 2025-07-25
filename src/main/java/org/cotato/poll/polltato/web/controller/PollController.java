package org.cotato.poll.polltato.web.controller;

import java.util.List;

import org.cotato.poll.polltato.domain.poll.enums.PollStatus;
import org.cotato.poll.polltato.domain.poll.service.PollService;
import org.cotato.poll.polltato.domain.poll.service.dto.PollDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Operation(summary = "투표 상태 변경", description = "투표의 상태를 변경합니다. (예: 시작, 종료)")
	@PatchMapping("/{pollId}/status")
	public ResponseEntity<?> updatePollStatus(@PathVariable("pollId") final Long pollId,
		@RequestParam("status") PollStatus status) {
		pollService.updatePollStatus(pollId, status);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "특정 투표에 참여한 인원에게 투표 안내 메일 발송", description = "특정 투표에 참여한 인원에게 투표 안내 메일을 발송합니다.")
	@PostMapping("/{pollId}/send-notification")
	public ResponseEntity<Void> sendPollNotification(@PathVariable("workspaceId") final Long workspaceId,
		@PathVariable("pollId") final Long pollId) {
		pollService.sendPollNotification(workspaceId, pollId);
		return ResponseEntity.ok().build();
	}
}
