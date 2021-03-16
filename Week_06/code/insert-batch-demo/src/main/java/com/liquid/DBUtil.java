package com.liquid;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库工具类
 *
 * @author Liquid
 */
public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/sharding_oms_order?rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final long CONN_TIMEOUT = 60 * 1000;
    private static final long IDLE_TIMEOUT = 10 * 60 * 1000;
    private static final int MAXIMUN_POOL_SIZE = 20;

    private static HikariDataSource dataSource;

    //初始化DataSource
    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setDriverClassName(DRIVER);
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USER);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setConnectionTimeout(CONN_TIMEOUT);
        hikariConfig.setMaximumPoolSize(MAXIMUN_POOL_SIZE);
        hikariConfig.setIdleTimeout(IDLE_TIMEOUT);
        //用于测试
        hikariConfig.setAutoCommit(false);
        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
