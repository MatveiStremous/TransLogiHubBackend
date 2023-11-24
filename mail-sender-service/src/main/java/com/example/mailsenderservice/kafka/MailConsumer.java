package com.example.mailsenderservice.kafka;

import com.example.mailsenderservice.dto.MailKafkaDto;
import com.example.mailsenderservice.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MailConsumer {
    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(topics = "${topic.name}", groupId = "${topic-group.name")
    public void consumeMessage(String message) {
        MailKafkaDto mailKafkaDto = objectMapper.readValue(message, MailKafkaDto.class);
        mailService.send(mailKafkaDto);
    }
}
