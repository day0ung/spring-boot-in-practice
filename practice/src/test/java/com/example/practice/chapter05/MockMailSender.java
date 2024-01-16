package com.example.practice.chapter05;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

// 5.4 목 오브젝트 - 메일 발송 여부와 수신자를 기록하여 검증에 사용
public class MockMailSender implements MailSender {

    private final List<String> requests = new ArrayList<>();

    public List<String> getRequests() { return requests; }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        requests.add(simpleMessage.getTo()[0]);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {}
}
