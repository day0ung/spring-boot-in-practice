package com.example.practice.chapter06;

import com.example.practice.chapter05.User;
import com.example.practice.chapter05.UserDao;

import java.util.ArrayList;
import java.util.List;

// 6.2 고립된 단위 테스트 - DB 없이 UserDao를 흉내내는 목 오브젝트
public class MockUserDao extends UserDao {

    private final List<User> users;
    private final List<User> updated = new ArrayList<>();

    public MockUserDao(List<User> users) {
        super(); // DataSource 불필요
        this.users = users;
    }

    public List<User> getUpdated() { return updated; }

    @Override
    public List<User> getAll() { return users; }

    @Override
    public void update(User user) { updated.add(user); }

    @Override
    public void add(User user) { throw new UnsupportedOperationException(); }

    @Override
    public User get(String id) { throw new UnsupportedOperationException(); }

    @Override
    public void deleteAll() { throw new UnsupportedOperationException(); }

    @Override
    public int getCount() { throw new UnsupportedOperationException(); }
}
