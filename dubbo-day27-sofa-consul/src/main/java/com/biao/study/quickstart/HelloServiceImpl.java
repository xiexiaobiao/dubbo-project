package com.biao.study.quickstart;

/**
 * @ClassName HelloServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String string) {
        System.out.println("Server receive: " + string);
        return "hello " + string + " ！";
    }
}
