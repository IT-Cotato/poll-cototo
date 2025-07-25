package org.cotato.poll.polltato.domain.poll.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PollStatus {
	OFF("초안"),
	ACTIVE("진행중"),
	CLOSED("종료");

	private final String description;
}
