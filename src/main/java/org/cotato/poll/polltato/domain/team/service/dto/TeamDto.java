package org.cotato.poll.polltato.domain.team.service.dto;

import org.cotato.poll.polltato.domain.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

	private Long id;

	private String name;

	private Long workspaceId;

	public static TeamDto from(Team team) {
		return TeamDto.builder()
			.id(team.getId())
			.name(team.getName())
			.workspaceId(team.getWorkspace().getId())
			.build();
	}
}
