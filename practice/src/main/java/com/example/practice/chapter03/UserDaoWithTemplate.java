package com.example.practice.chapter03;

import com.example.practice.chapter01.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

// 3.5 템플릿과 콜백 - 스프링의 JdbcTemplate이 템플릿/콜백 패턴의 실제 구현체
public class UserDaoWithTemplate {

    private final JdbcContext jdbcContext;
    private final JdbcTemplate jdbcTemplate;

    public UserDaoWithTemplate(DataSource dataSource) {
        this.jdbcContext = new JdbcContext(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // JdbcContext.executeSql()로 콜백 재활용
    public void deleteAll() throws SQLException {
        jdbcContext.executeSql("DELETE FROM users");
    }

    // JdbcTemplate - 스프링이 제공하는 완성된 템플릿/콜백
    public void add(User user) {
        jdbcTemplate.update(
            "INSERT INTO users(id, name, password, level, login, recommend) VALUES(?,?,?,1,0,0)",
            user.getId(), user.getName(), user.getPassword()
        );
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?",
            (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            },
            id
        );
    }
}
