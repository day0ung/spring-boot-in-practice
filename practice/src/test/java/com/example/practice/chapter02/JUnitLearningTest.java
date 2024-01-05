package com.example.practice.chapter02;

import com.example.practice.chapter01.DaoFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// 2.5 학습 테스트 - JUnit 인스턴스 생성 방식 & 스프링 컨텍스트 공유 검증
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class JUnitLearningTest {

    @Autowired
    ApplicationContext context;

    static Set<JUnitLearningTest> testObjects = new HashSet<>();
    static ApplicationContext contextObject = null;

    @Test
    void test1() {
        // JUnit은 테스트마다 새 인스턴스를 생성함
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        // 스프링 컨텍스트는 동일한 오브젝트 재사용
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }
}
