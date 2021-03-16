package com.liquid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * 测试类
 * 测试中使用数据大小或者字符串的长度较小
 *
 * @author Liquid
 */
public class Application {
    public static void main(String[] args) throws Exception {
        //===1.使用事务和PreparedStatement,addBatch()方法====
        //经过测试消耗时间大于1min
        //testTxAndPreparedstatement();


        //===2.使用事务和PreparedStatement,使用SQL语句拼接======
        //形如INSERT INTO oms_order (user_id,order_sn,order_status,consignee,mobile,address,product_price,freight_price) VALUES (...),(...)...;
        //经过测试消耗时间在20s上下
        //testTxAndPreparedstatement2();

        //===3.Load data方法===================================
        //使用csv文件测试,经过测试消耗时间在17s上下
        //生成csv文件
        //try {
        //    CreateCSVUtils.createCSVFile(new ArrayList<Object>(), null);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //testLoadData();


        //===4.单表插入,使用多线程加上Hikari连接池方法=========
        //经过调试在个人PC上设置20个线程,Hikari连接池最大连接数设置为20效果较好
        //消耗时间在16s上下
        long begin = System.currentTimeMillis();
        CountDownLatch downLatch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            final int segment = i;
            new Thread(() -> {
                testTxAndPreparedstatement3(segment);
                downLatch.countDown();
            }).start();
        }
        downLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("花费了 " + (end - begin) + " ms");

        //===5.将订单表存储引擎更换为MyISAM,使用方法2,3,4测试消耗时间小于10s,addBatch()方法操作比较慢==================
    }

    /**
     * 使用事务和PreparedStatment编译预处理语句(addBatch()方法)
     */
    private static void testTxAndPreparedstatement() {
        long begin = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SnowFlake snowFlake = new SnowFlake(1, 1);
        String sql = "INSERT INTO oms_order (user_id,order_sn,order_status,consignee,mobile,address,product_price,freight_price) VALUES (?,?,?,?,?,?,?,?)";
        try {
            //连接数据库并获取连接
            String url = "jdbc:mysql://localhost:3306/liquidmall?rewriteBatchedStatements=true";
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
                    //地址
                    preparedStatement.setObject(6, "shanghai");
                    //商品总额
                    preparedStatement.setObject(7, 1);
                    //运费
                    preparedStatement.setObject(8, 1);
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

    /**
     * 使用事务和PreparedStatment编译预处理语句(拼接SQL语句)
     */
    private static void testTxAndPreparedstatement2() {
        long begin = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SnowFlake snowFlake = new SnowFlake(1, 1);
        String prefix = "INSERT INTO oms_order (user_id,order_sn,order_status,consignee,mobile,address,product_price,freight_price) VALUES ";
        String sql = prefix + "(?,?,?,?,?,?,?,?)";
        try {
            //连接数据库并获取连接
            String url = "jdbc:mysql://localhost:3306/liquidmall?rewriteBatchedStatements=true";
            connection = DriverManager.getConnection(url, "root", "root");
            //关闭自动提交事务
            connection.setAutoCommit(false);
            StringBuffer suffix = null;
            //编译预处理，根据数据库连接对象获取SQL语句执行对象
            //经过测试connection.prepareStatement()多次调用获取的PreparedStatement对象不一样
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 10; i++) {
                suffix = new StringBuffer();
                for (int j = 0; j < 100000; j++) {
                    suffix.append("(1,1,1,1,1,1,1,1),");
                }
                String sqlTemp = prefix + suffix.substring(0, suffix.length() - 1);
                preparedStatement.addBatch(sqlTemp);
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

    /**
     * Load Data方法
     */
    private static void testLoadData() {
        long begin = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String url = "jdbc:mysql://localhost:3306/liquidmall";
            connection = DriverManager.getConnection(url, "root", "root");
            String sql = "LOAD DATA INFILE 'C:/Users/maolikui/Desktop/test/test.csv' INTO TABLE oms_order  " +
                    "FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' ignore 1 lines (user_id,order_sn,order_status,consignee,mobile,address,product_price,freight_price)";
            //通过数据库连接对象获取到执行SQL语句的对象
            statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        long end = System.currentTimeMillis();
        System.out.println("花费了 " + (end - begin) + " ms");
    }

    /**
     * 使用多线程
     */
    private static void testTxAndPreparedstatement3(int segment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SnowFlake snowFlake = new SnowFlake(1, 1);
        String prefix = "INSERT INTO oms_order (user_id,order_sn,order_status,consignee,mobile,address,goods_price,freight_price) VALUES ";
        String sql = prefix + "(?,?,?,?,?,?,?,?)";
        try {
            //连接数据库并获取连接
            // String url = "jdbc:mysql://localhost:3306/liquidmall?rewriteBatchedStatements=true";
            //connection = DriverManager.getConnection(url, "root", "root");
            connection = DBUtil.getConnection();
            //关闭自动提交事务
            //connection.setAutoCommit(false);
            //编译预处理，根据数据库连接对象获取SQL语句执行对象
            //经过测试connection.prepareStatement()多次调用获取的PreparedStatement对象不一样
            preparedStatement = connection.prepareStatement(sql);
            StringBuffer suffix = new StringBuffer();
            for (int j = 0; j < 50000; j++) {
                suffix.append("(" + (segment * 50000 + j + 1) + "," +
                        snowFlake.nextId() + "," +
                        "1,1,1,1,1,1),");
            }
            String sqlTemp = prefix + suffix.substring(0, suffix.length() - 1);
            preparedStatement.addBatch(sqlTemp);
            preparedStatement.executeBatch();
            //手动提交事务
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    DBUtil.closeConnection(connection);
                    //connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
