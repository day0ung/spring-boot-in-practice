package com.example.practice.chapter05;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    DataSource dataSource;

    private UserDao userDao;
    private UserServiceImpl userService;
    private List<User> users;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(dataSource);
        userService = new UserServiceImpl(userDao);

        users = Arrays.asList(
            new User("a", "유저A", "pw", Level.BASIC,  49, 0,  "a@test.com"),
            new User("b", "유저B", "pw", Level.BASIC,  50, 0,  "b@test.com"), // SILVER 대상
            new User("c", "유저C", "pw", Level.SILVER, 60, 29, "c@test.com"),
            new User("d", "유저D", "pw", Level.SILVER, 60, 30, "d@test.com"), // GOLD 대상
            new User("e", "유저E", "pw", Level.GOLD,   100, 100, "e@test.com")
        );
    }

    // 5.2 upgradeLevels() 검증
    @Test
    void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);  // BASIC → SILVER
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);  // SILVER → GOLD
        checkLevelUpgraded(users.get(4), false);

        // 5.4 메일 발송 검증
        List<String> requests = mockMailSender.getRequests();
        assertThat(requests).hasSize(2);
        assertThat(requests.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(requests.get(1)).isEqualTo(users.get(3).getEmail());
    }

    // 5.1 add() 기본 레벨 설정 검증
    @Test
    void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4); // GOLD
        User userWithoutLevel = new User("new", "신규", "pw", null, 0, 0, "new@test.com");

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        assertThat(userDao.get(userWithLevel.getId()).getLevel()).isEqualTo(Level.GOLD);
        assertThat(userDao.get(userWithoutLevel.getId()).getLevel()).isEqualTo(Level.BASIC);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User updatedUser = userDao.get(user.getId());
        if (upgraded) assertThat(updatedUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        else assertThat(updatedUser.getLevel()).isEqualTo(user.getLevel());
    }
}
