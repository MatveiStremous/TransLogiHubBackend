package com.example.mailsenderservice.service.impl;

import com.example.mailsenderservice.dto.MailKafkaDto;
import com.example.mailsenderservice.service.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.application.name}")
    private String applicationName;

    @Override
    @SneakyThrows
    public void send(MailKafkaDto mailKafkaDto) {
        if (mailKafkaDto.getConsumerEmail() != null && !mailKafkaDto.getConsumerEmail().isEmpty()) {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setFrom(username, applicationName);
            helper.setTo(mailKafkaDto.getConsumerEmail());
            helper.setSubject(mailKafkaDto.getTopicName());
            helper.setText(mailKafkaDto.getMessage());
            mailSender.send(mailMessage);
        }
    }
}
