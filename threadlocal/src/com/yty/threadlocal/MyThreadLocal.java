package com.yty.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {
    private Map<Thread, Object> map = new HashMap<>();
    public void set(Object obj){
        map.put(Thread.currentThread(), obj);
    }
    public Object get(){
        return map.get(Thread.currentThread());
    }

    public void remove(){
        map.remove(Thread.currentThread());
    }
}
