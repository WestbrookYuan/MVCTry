package com.yty.threadlocal;

public class DBUtil {
    private static ThreadLocal<Connection> myThreadLocal = new ThreadLocal<>();
    public static Connection getConnection(){
        Connection conn = myThreadLocal.get();
        if (conn == null){
            conn = new Connection();
            myThreadLocal.set(conn);
        }
        return conn;
    }
}
