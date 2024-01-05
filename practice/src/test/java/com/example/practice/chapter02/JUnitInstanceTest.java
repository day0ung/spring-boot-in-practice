package com.example.practice.chapter02;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// 2.5 학습 테스트 - JUnit은 테스트마다 새 인스턴스를 생성함을 검증
class JUnitInstanceTest {

    static Set<JUnitInstanceTest> testObjects = new HashSet<>();

    @Test
    void test1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
    }

    @Test
    void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
    }

    @Test
    void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
    }
}
