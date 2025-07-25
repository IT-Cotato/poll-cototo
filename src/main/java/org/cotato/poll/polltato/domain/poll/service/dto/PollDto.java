package org.cotato.poll.polltato.domain.poll.service.dto;

import org.cotato.poll.polltato.domain.poll.entity.Poll;
import org.cotato.poll.polltato.domain.poll.enums.PollStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PollDto {

	private Long id;

	private String title;

	private String description;

	private Integer totalScore;

	private PollStatus status;

	private Long workspaceId;

	public static PollDto from(final Poll poll) {
		return PollDto.builder()
			.id(poll.getId())
			.title(poll.getTitle())
			.description(poll.getDescription())
			.totalScore(poll.getTotalScore())
			.status(poll.getStatus())
			.workspaceId(poll.getWorkspace().getId())
			.build();
	}
}
