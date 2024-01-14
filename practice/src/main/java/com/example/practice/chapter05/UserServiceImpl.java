package com.example.practice.chapter05;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

// 5.2 비즈니스 로직 담당 - 트랜잭션 코드 없이 순수 업그레이드 로직만 존재
public class UserServiceImpl implements UserService {

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    private final UserDao userDao;
    private MailSender mailSender;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) upgradeLevel(user);
        }
    }

    private boolean canUpgradeLevel(User user) {
        return switch (user.getLevel()) {
            case BASIC -> user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER;
            case SILVER -> user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD;
            case GOLD -> false;
        };
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeMail(user); // 5.4 메일 발송
    }

    // 5.4 MailSender 인터페이스를 통한 메일 발송 추상화
    private void sendUpgradeMail(User user) {
        if (mailSender == null) return;
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom("admin@example.com");
        msg.setSubject("업그레이드 안내");
        msg.setText("등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");
        mailSender.send(msg);
    }

    @Override
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    @Override
    public User get(String id) { return userDao.get(id); }

    @Override
    public List<User> getAll() { return userDao.getAll(); }
}
