package org.cotato.poll.polltato.domain.team.service.dto;

import org.cotato.poll.polltato.domain.team.entity.TeamUser;
import org.cotato.poll.polltato.domain.team.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamUserDto {

	private Long teamUserId;

	private Long userId;

	private Long teamId;

	private String teamName;

	private Role role;

	public static TeamUserDto from(TeamUser teamUser) {
		return TeamUserDto.builder()
			.teamUserId(teamUser.getId())
			.userId(teamUser.getUserId())
			.teamId(teamUser.getTeam().getId())
			.role(teamUser.getRole())
			.teamName(teamUser.getTeam().getName())
			.build();
	}
}
