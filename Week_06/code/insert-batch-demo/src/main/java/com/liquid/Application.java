package com.liquid;

import java.sql.*;

/**
 * 测试类
 *
 * @author Liquid
 */
public class Application {
    public static void main(String[] args) {
        //使用JDBC原生接口，实现数据库的增删查改操作
        // testCURD();

        //使用事务和PreparedStatement
        testTxAndPreparedstatement();

        //使用HikariCP
        // testHikariCP();
    }

    /**
     * JDBC原生接口实现数据库访问操作
     *
     * @throws SQLException
     */
    private static void testCURD() {
        Connection connection = null;
        Statement statement = null;
        try {
            //面向过程一步一步执行
            //1.连接数据库并获取连接
            String url = "jdbc:mysql://192.168.19.130:3306/ssm";
            connection = DriverManager.getConnection(url, "root", "123456");

            //通过数据库连接对象获取到执行SQL语句的对象
            statement = connection.createStatement();

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4.释放资源
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用事务和PreparedStatment编译预处理语句
     *
     * @throws SQLException
     */
    private static void testTxAndPreparedstatement() {
        long begin = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SnowFlake snowFlake = new SnowFlake(1, 1);
        String sql = "INSERT INTO oms_order (user_id,order_sn,order_status,consignee,mobile,address,product_price,freight_price) VALUES (?,?,?,?,?,?,?,?)";
        try {
            //连接数据库并获取连接
            String url = "jdbc:mysql://localhost:3306/ssm";
            connection = DriverManager.getConnection(url, "root", "root");
            //关闭自动提交事务
            connection.setAutoCommit(false);
            //编译预处理，根据数据库连接对象获取SQL语句执行对象
            //经过测试connection.prepareStatement()多次调用获取的PreparedStatement对象不一样
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 10000; j++) {
                    //用户ID
                    preparedStatement.setObject(1, (j + 1 + i * 10000));
                    //订单ID
                    preparedStatement.setObject(2, snowFlake.nextId());
                    //订单状态
                    preparedStatement.setObject(3, "101");
                    //收货人姓名
                    preparedStatement.setObject(4, "xiao" + (j + 1 + i * 10000));
                    //手机号
                    preparedStatement.setObject(5, (j + 1 + i * 10000));
                    //商品总额
                    preparedStatement.setObject(6, 1);
                    //运费
                    preparedStatement.setObject(7, 1);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                //手动提交事务
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("花费了 " + (end - begin) + " ms");
    }
}
