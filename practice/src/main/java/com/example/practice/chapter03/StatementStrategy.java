package com.example.practice.chapter03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 3.2 변하는 것과 변하지 않는 것 - 전략 인터페이스
public interface StatementStrategy {
    PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
