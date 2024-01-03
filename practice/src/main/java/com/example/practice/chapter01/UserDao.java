package com.example.practice.chapter01;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

// 1.7 의존관계 주입(DI) - DataSource를 외부에서 주입받음
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        jdbcTemplate.update(
            "INSERT INTO users(id, name, password, level, login, recommend) VALUES(?,?,?,1,0,0)",
            user.getId(), user.getName(), user.getPassword()
        );
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

    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM users");
    }
}
