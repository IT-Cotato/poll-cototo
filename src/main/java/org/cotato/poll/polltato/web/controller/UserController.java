package org.cotato.poll.polltato.web.controller;

import org.cotato.poll.polltato.domain.team.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

}
