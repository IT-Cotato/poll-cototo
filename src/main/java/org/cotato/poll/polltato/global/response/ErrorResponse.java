package org.cotato.poll.polltato.global.response;

import java.util.List;

import org.cotato.poll.polltato.global.excepction.ErrorCode;

public record ErrorResponse(
	int status,
	String code,
	String message,
	List<String> reasons
) {
	public static ErrorResponse from(ErrorCode exceptionCode) {
		return new ErrorResponse(
			exceptionCode.getStatus().value(),
			exceptionCode.getCode(),
			exceptionCode.getMessage(),
			null
		);
	}

	public static ErrorResponse of(ErrorCode errorCode, List<String> messages) {
		return new ErrorResponse(
			errorCode.getStatus().value(),
			errorCode.getCode(),
			errorCode.getMessage(),
			messages
		);
	}
}
