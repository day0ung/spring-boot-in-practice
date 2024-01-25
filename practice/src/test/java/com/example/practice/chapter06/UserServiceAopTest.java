package com.example.practice.chapter06;

import com.example.practice.chapter05.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 6.8 트랜잭션 지원 테스트 - @Transactional 로 테스트 후 자동 롤백
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceAopTest {

    @Autowired
    DataSource dataSource;

    private UserServiceAop userService;
    private UserDao userDao;
    private List<User> users;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(dataSource);
        userService = new UserServiceAop(userDao);
        userService.setMailSender(new DummyMailSender());

        users = Arrays.asList(
            new User("a", "유저A", "pw", Level.BASIC,  49, 0,  "a@test.com"),
            new User("b", "유저B", "pw", Level.BASIC,  50, 0,  "b@test.com"),
            new User("c", "유저C", "pw", Level.SILVER, 60, 29, "c@test.com"),
            new User("d", "유저D", "pw", Level.SILVER, 60, 30, "d@test.com"),
            new User("e", "유저E", "pw", Level.GOLD,   100, 100, "e@test.com")
        );
    }

    // 6.5 AOP @Transactional 적용된 upgradeLevels() 검증
    @Test
    void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        userService.upgradeLevels();

        assertThat(userDao.get("a").getLevel()).isEqualTo(Level.BASIC);
        assertThat(userDao.get("b").getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get("c").getLevel()).isEqualTo(Level.SILVER);
        assertThat(userDao.get("d").getLevel()).isEqualTo(Level.GOLD);
        assertThat(userDao.get("e").getLevel()).isEqualTo(Level.GOLD);
    }

    // 6.8 @Transactional readOnly - get() 읽기 전용 트랜잭션
    @Test
    void getUser() {
        userDao.deleteAll();
        userDao.add(users.get(0));

        User found = userService.get("a");
        assertThat(found.getName()).isEqualTo("유저A");
    }

    // 6.8 add() 기본 레벨 설정 검증
    @Test
    void add() {
        userDao.deleteAll();

        User newUser = new User("new", "신규", "pw", null, 0, 0, "new@test.com");
        userService.add(newUser);

        assertThat(userDao.get("new").getLevel()).isEqualTo(Level.BASIC);
    }
}
