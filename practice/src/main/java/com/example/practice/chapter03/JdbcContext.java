package com.example.practice.chapter03;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 3.4 컨텍스트와 DI - JdbcContext로 분리하여 여러 DAO에서 재사용 가능
public class JdbcContext {

    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 템플릿: 변하지 않는 JDBC 흐름
    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
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

    // 3.5 콜백 재활용 - SQL만 받아 실행하는 편의 메서드
    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(c -> c.prepareStatement(query));
    }
}
