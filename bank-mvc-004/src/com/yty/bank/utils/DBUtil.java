package com.yty.bank.utils;


import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");
    private DBUtil(){}

    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    public static Connection getConnection() throws SQLException{
        Connection conn = threadLocal.get();
        if(conn == null){
            conn = DriverManager.getConnection(url, user, password);
            threadLocal.set(conn);
        }

        return conn;
    }
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn != null){
            try {
                conn.close();
                threadLocal.remove();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
