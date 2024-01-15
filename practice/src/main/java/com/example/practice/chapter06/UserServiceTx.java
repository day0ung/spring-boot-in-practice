package com.example.practice.chapter06;

import com.example.practice.chapter05.User;
import com.example.practice.chapter05.UserService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

// 6.1 트랜잭션 코드의 분리 - 데코레이터 패턴으로 트랜잭션만 담당
public class UserServiceTx implements UserService {

    private final UserService userService; // 비즈니스 로직 위임
    private final PlatformTransactionManager transactionManager;

    public UserServiceTx(UserService userService, PlatformTransactionManager transactionManager) {
        this.userService = userService;
        this.transactionManager = transactionManager;
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status =
            transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels(); // 비즈니스 로직 위임
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void add(User user) { userService.add(user); }

    @Override
    public User get(String id) { return userService.get(id); }

    @Override
    public List<User> getAll() { return userService.getAll(); }
}
