package com.example.practice.chapter01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

// 1.5 스프링의 IoC - ApplicationContext를 이용한 UserDao 테스트
class UserDaoTest {

    private UserDao dao;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.dao = context.getBean("userDao", UserDao.class);
    }

    @Test
    void addAndGet() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User user = new User("user1", "홍길동", "password");
        dao.add(user);
        assertThat(dao.getCount()).isEqualTo(1);

        User found = dao.get("user1");
        assertThat(found.getName()).isEqualTo(user.getName());
        assertThat(found.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void count() {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(new User("u1", "유저1", "pw1"));
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(new User("u2", "유저2", "pw2"));
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(new User("u3", "유저3", "pw3"));
        assertThat(dao.getCount()).isEqualTo(3);
    }
}
