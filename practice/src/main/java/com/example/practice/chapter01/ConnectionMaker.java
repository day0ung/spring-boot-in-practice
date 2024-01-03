package com.example.practice.chapter01;

import java.sql.Connection;
import java.sql.SQLException;

// 1.3 DAO의 확장 - DB 연결 전략 인터페이스
public interface ConnectionMaker {
    Connection makeConnection() throws SQLException;
}
