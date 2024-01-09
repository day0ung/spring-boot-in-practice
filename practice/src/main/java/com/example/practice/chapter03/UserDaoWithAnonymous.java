package com.example.practice.chapter03;

import com.example.practice.chapter01.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 3.3 JDBC 전략 패턴의 최적화 - 익명 내부 클래스로 전략 클래스 제거
public class UserDaoWithAnonymous {

    private final DataSource dataSource;

    public UserDaoWithAnonymous(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (c != null) try { c.close(); } catch (SQLException ignored) {}
        }
    }

    // 3.3 익명 내부 클래스: 별도 클래스 없이 인라인으로 전략 구현
    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy(
            new StatementStrategy() {
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                    return c.prepareStatement("DELETE FROM users");
                }
            }
        );
    }

    // 람다로 더 간결하게 표현 (함수형 인터페이스)
    public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users(id, name, password, level, login, recommend) VALUES(?,?,?,1,0,0)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            return ps;
        });
    }
}
