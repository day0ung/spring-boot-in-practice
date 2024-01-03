package com.example.practice.chapter01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ConnectionMaker 구현체 - 로컬(H2) DB 연결
public class LocalDBConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }
}
