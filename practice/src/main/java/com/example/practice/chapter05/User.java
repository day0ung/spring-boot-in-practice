package com.example.practice.chapter05;

// 5.1 사용자 레벨 관리 기능 추가 - level, login, recommend, email 필드 추가
public class User {
    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;
    private String email;

    public User() {}

    public User(String id, String name, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    // 5.1 레벨 업그레이드 로직을 User 오브젝트 내부로 위임
    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if (nextLevel == null) throw new IllegalStateException(this.level + "은 업그레이드 불가");
        this.level = nextLevel;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }
    public int getLogin() { return login; }
    public void setLogin(int login) { this.login = login; }
    public int getRecommend() { return recommend; }
    public void setRecommend(int recommend) { this.recommend = recommend; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
