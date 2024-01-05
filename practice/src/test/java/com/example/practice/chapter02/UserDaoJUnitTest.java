package com.example.practice.chapter02;

import com.example.practice.chapter01.DaoFactory;
import com.example.practice.chapter01.User;
import com.example.practice.chapter01.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 2.3 JUnit - @BeforeEach 픽스처, 일관된 테스트 결과 보장
class UserDaoJUnitTest {

    private UserDao dao;

    // 픽스처
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.dao = context.getBean("userDao", UserDao.class);

        this.user1 = new User("id1", "유저1", "pw1");
        this.user2 = new User("id2", "유저2", "pw2");
        this.user3 = new User("id3", "유저3", "pw3");
    }

    @Test
    void addAndGet() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        User found = dao.get(user1.getId());
        assertThat(found.getName()).isEqualTo(user1.getName());
        assertThat(found.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    void count() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);
    }

    // 2.3 예외조건 테스트 - 존재하지 않는 id로 get() 호출 시 예외 발생
    @Test
    void getUserFailure() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> dao.get("unknown_id"))
            .isInstanceOf(Exception.class);
    }
}
