package org.cotato.poll.polltato.global.excepction;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "E-001", "요청한 엔티티를 찾을 수 없습니다."),
	USER_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "C-001", "사용자 입력 오류입니다."),
	UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "예기치 못한 오류가 발생했습니다."),
	// poll
	NOT_IN_WORKSPACE(HttpStatus.FORBIDDEN, "P-001", "해당 워크스페이스에 속하지 않은 투표 입니다."),
	EMAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-002", "이메일 전송 중 오류가 발생했습니다."),
	;

	private final HttpStatus status;
	private final String code;
	private final String message;
}
