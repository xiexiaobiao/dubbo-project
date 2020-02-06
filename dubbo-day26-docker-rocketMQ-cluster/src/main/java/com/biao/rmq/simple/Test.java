package com.biao.rmq.simple;

import java.util.concurrent.atomic.AtomicReference;

public class Test {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    public static void main(String[] args) {



    }

    void lock(){
        Thread current = Thread.currentThread();
        for (;;){
            if ( !owner.compareAndSet(null,current)){
                return;
            }
        }
    }

    void unlock(){
        Thread current = Thread.currentThread();
        owner.compareAndSet(current,null);
    }
}
