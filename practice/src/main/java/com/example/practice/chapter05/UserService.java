package com.example.practice.chapter05;

import java.util.List;

// 5.3 단일 책임 원칙 - 인터페이스로 분리하여 트랜잭션/비즈니스 로직 구현체를 교체 가능하게
public interface UserService {
    void add(User user);
    void upgradeLevels();
    User get(String id);
    List<User> getAll();
}
