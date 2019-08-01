package com.example.common.api;

public interface GreetingService {

    public String greeting(String name);

    default String replyGreeting(String name) {
        return "default method: Fine, " + name;
    }

}
