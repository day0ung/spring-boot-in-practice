package com.example.practice.chapter03;

import com.example.practice.chapter01.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 3.2 전략 패턴 - 변하지 않는 컨텍스트(자원 관리) / 변하는 전략(SQL 로직) 분리
public class UserDaoWithStrategy {

    private final DataSource dataSource;

    public UserDaoWithStrategy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 컨텍스트: 변하지 않는 흐름
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
            if (c != null) try { c.close(); } catch (SQLException ignored) {}
        }
    }

    // 클라이언트: 전략을 선택하고 컨텍스트 호출
    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy(c -> c.prepareStatement("DELETE FROM users"));
    }

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
