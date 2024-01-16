package com.example.practice.chapter05;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

// 5.4 테스트용 DummyMailSender - 아무 동작도 하지 않는 테스트 대역(스텁)
public class DummyMailSender implements MailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {}

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {}
}
