package com.biao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname Application
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-11-23 19:04
 * @Version 1.0
 **/
public class Application1 {
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
        // 过滤
        Stream<Fruit> filteredStream = fruitStream.filter(d -> "China".equals(d.getOrigin()));
        // 去掉重复元素
        Stream<Fruit> distinctStream = filteredStream.distinct();
        // 打印流中元素，forEach是终端操作，如果这里使用了，则collect方法无法使用,即一个流只能消费一次
        // distinctStream.forEach(System.out::println);
        // 跳过1个元素，
        Stream<Fruit> skippedStream = distinctStream.skip(1);
        // 切片,参数为maxSize
        Stream<Fruit> limitStream = skippedStream.limit(4);
        // 结束，collect方法是收集器，如果这里使用了，则forEach无法使用，即一个流只能有一个终端操作
        List<Fruit> newList = limitStream.collect(Collectors.toList());
        // 打印结果，lambda方式
        newList.forEach(System.out::println);

        // 链式操作,和上面效果一样，一气呵成，真爽！
        List<Fruit> newList2 = fruitList.stream()
                .filter(d -> "China".equals(d.getOrigin()))
                .distinct()
                .skip(1)
                .limit(4)
                .collect(Collectors.toList());
        // 打印结果集
        newList2.forEach(System.out::println);
    }

}
