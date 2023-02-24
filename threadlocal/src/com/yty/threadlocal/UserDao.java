package com.yty.threadlocal;

public class UserDao {
    public void insert(){
        Thread thread = Thread.currentThread();
        System.out.println(thread);
        System.out.println(DBUtil.getConnection());
        System.out.println("user insert");
    }
}
