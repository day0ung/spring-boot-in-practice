package com.example.practice.chapter06;

import com.example.practice.chapter05.Level;
import com.example.practice.chapter05.User;
import com.example.practice.chapter05.UserDao;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 6.5~6.7 스프링 AOP + @Transactional - 가장 간결한 트랜잭션 적용 방식
@Transactional // 6.7 클래스 레벨 기본 트랜잭션 속성
public class UserServiceAop {

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    private final UserDao userDao;
    private MailSender mailSender;

    public UserServiceAop(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 6.6 트랜잭션 속성 - 쓰기 작업
    @Transactional(rollbackFor = Exception.class)
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) upgradeLevel(user);
        }
    }

    // 6.6 읽기 전용 트랜잭션으로 성능 최적화
    @Transactional(readOnly = true)
    public User get(String id) {
        return userDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
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
        sendUpgradeMail(user);
    }

    private void sendUpgradeMail(User user) {
        if (mailSender == null) return;
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("업그레이드 안내");
        msg.setText("등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");
        mailSender.send(msg);
    }
}
