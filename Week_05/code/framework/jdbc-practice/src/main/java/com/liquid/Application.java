package com.liquid;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import java.sql.*;

/**
 * 测试类
 *
 * @author Liquid
 */
public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        //使用JDBC原生接口，实现数据库的增删查改操作
        // testCURD();

        //使用事务和PreparedStatement
        // testTxAndPreparedstatement();

        //使用HikariCP
        // testHikariCP();

        //封装DBUtil测试
        DemoService demoService = new DemoServiceImpl();
        // demoService.updateBookNums(1004, 1011);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                demoService.updateBookNums(1004, 10000);
            }).start();
        }
    }

    /**
     * JDBC原生接口实现数据库访问操作
     *
     * @throws SQLException
     */
    private static void testCURD() throws SQLException {
        //面向过程一步一步执行
        //1.连接数据库并获取连接
        String url = "jdbc:mysql://192.168.19.130:3306/ssm";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        //通过数据库连接对象获取到执行SQL语句的对象
        Statement statement = connection.createStatement();

        //2.执行SQL语句(增删查改)
        //insert
        // int row = statement.executeUpdate("INSERT INTO book (book_id,name,number) values (1004,'JDBC-Practice',10)");
        //delete
        // int row = statement.executeUpdate("DELETE from book where book_id = 1004");
        //query
        ResultSet resultSet = statement.executeQuery("select * from book");
        //3.处理结果集
        while (resultSet.next()) {
            int bookId = resultSet.getInt("book_id");
            String name = resultSet.getString("name");
            int number = resultSet.getInt("number");
            System.out.println("bookId: " + bookId + ",name: " + name + ",number: " + number);
        }

        //update
        // int row = statement.executeUpdate("UPDATE book SET number = 100 where book_id = 1004");

        //4.释放资源
        statement.close();
        connection.close();
    }

    /**
     * 使用事务和PreparedStatment编译预处理语句
     *
     * @throws SQLException
     */
    private static void testTxAndPreparedstatement() throws SQLException {
        //连接数据库并获取连接
        String url = "jdbc:mysql://192.168.19.130:3306/ssm";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        //关闭自动提交事务
        connection.setAutoCommit(false);

        //编译预处理，根据数据库连接对象获取SQL语句执行对象
        //经过测试connection.prepareStatement()多次调用获取的PreparedStatement对象不一样
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book (book_id,name,number) values (?,?,?)");
        preparedStatement.setObject(1, "1004");
        preparedStatement.setObject(2, "JDBC-Practice-4");
        preparedStatement.setObject(3, "10");
        preparedStatement.addBatch();

        for (int i = 5; i < 100; i++) {
            preparedStatement.setObject(1, 1000 + i);
            preparedStatement.setObject(2, "JDBC-Practice-" + i);
            preparedStatement.setObject(3, 10);
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        //手动提交事务
        connection.commit();

        //释放资源
        preparedStatement.close();
        connection.close();
    }

    /**
     * 使用HikariCP
     *
     * @throws SQLException
     */
    private static void testHikariCP() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://192.168.19.130:3306/ssm");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        hikariConfig.setConnectionTimeout(60 * 1000);
        //1.配置DataSource
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        //2.根据DataSource获取数据库连接对象
        Connection connection = dataSource.getConnection();
        //关闭自动提交事务
        connection.setAutoCommit(false);
        //编译预处理，根据数据库连接对象获取SQL语句执行对象
        //经过测试connection.prepareStatement()多次调用获取的PreparedStatement对象不一样
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book (book_id,name,number) values (?,?,?)");
        preparedStatement.setObject(1, "1004");
        preparedStatement.setObject(2, "JDBC-Practice-4");
        preparedStatement.setObject(3, "10");
        preparedStatement.addBatch();
        //批处理
        for (int i = 5; i < 100; i++) {
            preparedStatement.setObject(1, 1000 + i);
            preparedStatement.setObject(2, "JDBC-Practice-" + i);
            preparedStatement.setObject(3, 10);
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        //手动提交事务
        connection.commit();
        preparedStatement.close();
        connection.close();
        dataSource.close();
    }

}
