package com.example.practice.chapter02;

import com.example.practice.chapter01.DaoFactory;
import com.example.practice.chapter01.User;
import com.example.practice.chapter01.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

// 2.4 스프링 테스트 적용 - ApplicationContext 공유, @Autowired
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoSpringTest {

    @Autowired
    private UserDao dao; // ApplicationContext 없이 직접 주입

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("spring1", "스프링1", "pw1");
        user2 = new User("spring2", "스프링2", "pw2");
    }

    @Test
    void addAndGet() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User found = dao.get(user1.getId());
        assertThat(found.getName()).isEqualTo(user1.getName());
    }

    @Test
    void count() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);
    }
}
