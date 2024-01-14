package com.example.practice.chapter05;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 테스트용 MockUserDao에서 상속 시 사용
    protected UserDao() {
        this.jdbcTemplate = null;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    public void add(User user) {
        jdbcTemplate.update(
            "INSERT INTO users(id, name, password, level, login, recommend, email) VALUES(?,?,?,?,?,?,?)",
            user.getId(), user.getName(), user.getPassword(),
            user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail()
        );
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", userRowMapper, id);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY id", userRowMapper);
    }

    // 5.1 update() 추가 - 레벨 업그레이드 시 사용
    public void update(User user) {
        jdbcTemplate.update(
            "UPDATE users SET name=?, password=?, level=?, login=?, recommend=?, email=? WHERE id=?",
            user.getName(), user.getPassword(), user.getLevel().intValue(),
            user.getLogin(), user.getRecommend(), user.getEmail(), user.getId()
        );
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }
}
