package org.cotato.poll.polltato.global.excepction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
}
