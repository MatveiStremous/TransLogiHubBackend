package com.example.mailsenderservice.service.impl;

import com.example.mailsenderservice.dto.MailKafkaDto;
import com.example.mailsenderservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public void send(MailKafkaDto mailKafkaDto) {
        if (mailKafkaDto.getConsumerEmail() != null && !mailKafkaDto.getConsumerEmail().isEmpty()) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mailKafkaDto.getConsumerEmail());
            simpleMailMessage.setSubject(mailKafkaDto.getTopicName());
            simpleMailMessage.setText(mailKafkaDto.getMessage());
            mailSender.send(simpleMailMessage);
        }
    }
}
