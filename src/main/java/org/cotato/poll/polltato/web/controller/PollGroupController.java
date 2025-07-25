package org.cotato.poll.polltato.web.controller;

import org.cotato.poll.polltato.domain.poll.service.PollGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/poll-groups")
@RequiredArgsConstructor
public class PollGroupController {

	private final PollGroupService pollGroupService;
}
