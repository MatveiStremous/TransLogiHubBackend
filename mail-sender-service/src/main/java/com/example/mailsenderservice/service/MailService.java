package com.example.mailsenderservice.service;

import com.example.mailsenderservice.dto.MailKafkaDto;

public interface MailService {
    void send(MailKafkaDto mailKafkaDto);
}
