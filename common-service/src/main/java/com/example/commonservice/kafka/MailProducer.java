package com.example.commonservice.kafka;

import com.example.commonservice.kafka.dto.MailKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailProducer {
    private final KafkaTemplate<String, MailKafkaDto> objectKafkaDtoTemplate;

    @Value("${topic.name}")
    private String topicName;

    @SneakyThrows
    public void sendMailKafkaDtoToConsumer(MailKafkaDto mailKafkaDto) {
        objectKafkaDtoTemplate.send(topicName, mailKafkaDto);
    }
}
