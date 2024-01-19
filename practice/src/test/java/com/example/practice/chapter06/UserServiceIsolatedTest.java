package com.example.practice.chapter06;

import com.example.practice.chapter05.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 6.2 고립된 단위 테스트 - DB/스프링 컨텍스트 없이 순수하게 UserServiceImpl만 테스트
class UserServiceIsolatedTest {

    private UserServiceImpl userService;
    private MockUserDao mockUserDao;
    private MockMailSender mockMailSender;
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
            new User("a", "유저A", "pw", Level.BASIC,  49, 0,  "a@test.com"),
            new User("b", "유저B", "pw", Level.BASIC,  50, 0,  "b@test.com"),
            new User("c", "유저C", "pw", Level.SILVER, 60, 29, "c@test.com"),
            new User("d", "유저D", "pw", Level.SILVER, 60, 30, "d@test.com"),
            new User("e", "유저E", "pw", Level.GOLD,   100, 100, "e@test.com")
        );

        mockUserDao = new MockUserDao(users);
        mockMailSender = new MockMailSender();

        userService = new UserServiceImpl(mockUserDao);
        userService.setMailSender(mockMailSender);
    }

    @Test
    void upgradeLevels() {
        userService.upgradeLevels();

        // MockUserDao에 update() 호출 결과 검증 (DB 없이)
        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated).hasSize(2);
        checkUserAndLevel(updated.get(0), "b", Level.SILVER);
        checkUserAndLevel(updated.get(1), "d", Level.GOLD);

        // 메일 발송 검증
        assertThat(mockMailSender.getRequests()).hasSize(2);
        assertThat(mockMailSender.getRequests().get(0)).isEqualTo("b@test.com");
        assertThat(mockMailSender.getRequests().get(1)).isEqualTo("d@test.com");
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }
}
