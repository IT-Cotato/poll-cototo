package org.cotato.poll.polltato.domain.poll.service;

import java.io.UnsupportedEncodingException;

import org.cotato.poll.polltato.global.excepction.BusinessException;
import org.cotato.poll.polltato.global.excepction.ErrorCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {

	private static final String SENDER_EMAIL = "boysoeng@naver.com";

	private static final String SENDER_PERSONAL = "Polltato";

	private final JavaMailSender mailSender;

	public void sendEmail(String recipient, String messageBody, String subject) {
		try {
			MimeMessage message = mailSender.createMimeMessage();

			message.addRecipients(Message.RecipientType.TO, recipient);
			message.setSubject(subject);
			message.setText(messageBody, "utf-8", "html");
			message.setFrom(getInternetAddress());
			mailSender.send(message);
			log.info("이메일 전송 완료");
		} catch (MessagingException e) {
			throw new BusinessException(ErrorCode.EMAIL_SEND_ERROR);
		}
	}

	private InternetAddress getInternetAddress() {
		try {
			return new InternetAddress(SENDER_EMAIL, SENDER_PERSONAL);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
