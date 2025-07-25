package org.cotato.poll.polltato.domain.team.service.dto;

import org.cotato.poll.polltato.domain.team.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;

	private String email;

	private String name;

	public static UserDto from(User user) {
		return UserDto.builder()
			.id(user.getId())
			.build();
	}
}
