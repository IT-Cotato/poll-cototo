package org.cotato.poll.polltato.domain.poll.service;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class SessionKeyGenerator {

	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

	public String generateSessionKey(String email, Long pollId) {
		// 이메일과 pollId, 현재 시간을 조합하여 고유한 세션키 생성
		String combination = email + pollId + System.currentTimeMillis();

		// 추가 보안을 위해 랜덤 바이트 추가
		byte[] randomBytes = new byte[16];
		secureRandom.nextBytes(randomBytes);

		String randomString = base64Encoder.encodeToString(randomBytes);
		String finalKey = base64Encoder.encodeToString((combination + randomString).getBytes());

		// URL에 안전한 형태로 변환하고 길이 제한
		return finalKey.substring(0, Math.min(finalKey.length(), 32));
	}
} 
