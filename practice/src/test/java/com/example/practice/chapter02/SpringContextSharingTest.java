package com.example.practice.chapter02;

import com.example.practice.chapter01.DaoFactory;
import com.example.practice.chapter01.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

// 2.4 스프링 테스트 적용 - 컨텍스트 공유 및 @Autowired 검증
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class SpringContextSharingTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    UserDao userDao;

    static ApplicationContext contextObject = null;

    @Test
    void contextIsSame1() {
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    void contextIsSame2() {
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    void userDaoIsInjected() {
        assertThat(userDao).isNotNull();
    }
}
