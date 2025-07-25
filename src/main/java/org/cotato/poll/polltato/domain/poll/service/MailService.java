package org.cotato.poll.polltato.domain.poll.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${app.base-url}")
	private String baseUrl;

	@Async("mailTaskExecutor")
	public CompletableFuture<Void> sendPollNotificationMail(String toEmail, String pollTitle, String workspaceName,
		Long pollId, String sessionKey) {
		return CompletableFuture.runAsync(() -> {
				String pollUrl = generatePollUrl(toEmail, sessionKey, pollId);
				String htmlBody = createHtmlEmailBody(workspaceName, pollTitle, pollUrl);
				String subject = createEmailSubject(workspaceName, pollTitle);

				sendMimeMessage(toEmail, subject, htmlBody);
			})
			.thenRun(() -> log.info("Poll notification email sent successfully to: {}", toEmail))
			.exceptionally(ex -> {
				log.error("Failed to send poll notification email to: {}", toEmail, ex);
				return null;
			});
	}

	private String generatePollUrl(String email, String sessionKey, Long pollId) {
		return String.format("%s/poll?email=%s&sessionKey=%s&pollId=%d",
			baseUrl, email, sessionKey, pollId);
	}

	private String createEmailSubject(String workspaceName, String pollTitle) {
		return String.format("[%s] 새로운 투표가 생성되었습니다: %s", workspaceName, pollTitle);
	}

	private String createHtmlEmailBody(String workspaceName, String pollTitle, String pollUrl) {
		return String.format(
			"<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"    <meta charset='UTF-8'>" +
				"    <style>" +
				"        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
				"        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
				"        .header { background-color: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 20px; }"
				+
				"        .content { padding: 20px 0; }" +
				"        .button { " +
				"            display: inline-block; " +
				"            padding: 12px 24px; " +
				"            background-color: #007bff; " +
				"            color: white; " +
				"            text-decoration: none; " +
				"            border-radius: 5px; " +
				"            font-weight: bold; " +
				"            margin: 20px 0; " +
				"        }" +
				"        .button:hover { background-color: #0056b3; }" +
				"        .footer { font-size: 12px; color: #666; margin-top: 30px; }" +
				"    </style>" +
				"</head>" +
				"<body>" +
				"    <div class='container'>" +
				"        <div class='header'>" +
				"            <h2>🗳️ 새로운 투표가 생성되었습니다!</h2>" +
				"        </div>" +
				"        <div class='content'>" +
				"            <p>안녕하세요!</p>" +
				"            <p>워크스페이스 <strong>'%s'</strong>에 새로운 투표가 생성되었습니다.</p>" +
				"            <p><strong>투표 제목:</strong> %s</p>" +
				"            <p>아래 버튼을 클릭하여 지금 바로 투표에 참여해보세요!</p>" +
				"            <div style='text-align: center;'>" +
				"                <a href='%s' class='button'>투표 참여하기</a>" +
				"            </div>" +
				"            <p>감사합니다.</p>" +
				"        </div>" +
				"        <div class='footer'>" +
				"            <p>이 메일은 자동으로 발송된 메일입니다.</p>" +
				"        </div>" +
				"    </div>" +
				"</body>" +
				"</html>",
			workspaceName, pollTitle, pollUrl
		);
	}

	private void sendMimeMessage(String toEmail, String subject, String htmlBody) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(htmlBody, true);

			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create or send mime message", e);
		}
	}
} 
