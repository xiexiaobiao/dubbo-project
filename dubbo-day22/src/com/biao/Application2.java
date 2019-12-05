package com.biao;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname Application
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-11-23 19:04
 * @Version 1.0
 **/
public class Application2 {
    public static void main(String[] args) {
        List<Fruit> fruitList = Arrays.asList(
                new Fruit("Apple","China",37),
                new Fruit("Apple","China",37),
                new Fruit("Banana","Japan",52),
                new Fruit("Banana","Japan",52),
                new Fruit("Orange","America",98),
                new Fruit("Grape","China",55),
                new Fruit("Peach","China",20),
                new Fruit("Mango","China",35),
                new Fruit("Melon","China",16),
                new Fruit("Walnut","China",80),
                new Fruit("Olive","China",63)
        );
        // 创建流
        Stream<Fruit> fruitStream = fruitList.stream();
        //转换,变为String流
        Stream<String> stringStream = fruitStream.map(Fruit::getName);
        //过滤，名称以A开头
        Stream<String> filteredStream = stringStream.filter(str -> "A".equals(String.valueOf(str.charAt(0))));
        //终端操作,set自动去重复
        Set<String> stringSet = filteredStream.collect(Collectors.toSet());
        //打印结果集
        stringSet.forEach(System.out::println);

        //链式语法实现,请君想象下JDK7的实现，
        fruitList.stream()
                .map(Fruit::getName)
                .filter(str -> "A".equals(str.substring(0,1)))
                .collect(Collectors.toSet())
                .forEach(System.out::println);
    }

}
