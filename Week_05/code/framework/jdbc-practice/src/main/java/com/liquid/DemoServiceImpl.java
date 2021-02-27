package com.liquid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demo service实现类
 *
 * @author Liquid
 */
public class DemoServiceImpl implements DemoService {
    private static final String UPDATE_BOOK_SQL = "UPDATE book SET number = ? where book_id = ?";
    private static final String INSERT_LOG_SQL = "INSERT INTO log (description,created) values(?,?)";


    @Override
    public void updateBookNums(int bookId, int number) {
        Connection connection = DBUtil.getConnection();
        System.out.println("Thread name: " + Thread.currentThread().getName() + ",获取Connection: " + connection);
        try {
            //connection.setAutoCommit(false);
            updateBook(connection, UPDATE_BOOK_SQL, bookId, number);
            insertLog(connection, INSERT_LOG_SQL, "Update book number.");
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    private void insertLog(Connection connection, String insertLogSql, String description) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(insertLogSql);
        preparedStatement.setObject(1, description);
        preparedStatement.setObject(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int row = preparedStatement.executeUpdate();
        if (row != 0) {
            System.out.println("Thread name: " + Thread.currentThread().getName() + " Insert log success");
        }
        preparedStatement.close();
    }

    private void updateBook(Connection connection, String updateBookSql, int bookId, int number) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(updateBookSql);
        preparedStatement.setObject(1, number);
        preparedStatement.setObject(2, bookId);
        int row = preparedStatement.executeUpdate();
        if (row != 0) {
            System.out.println("Thread name: " + Thread.currentThread().getName() + "update book number success");
        }
        preparedStatement.close();
    }
}
