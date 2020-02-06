package com.biao;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname Application
 * @Description 映射示例2：map映射+flatMap扁平化流
 * @Author xiexiaobiao
 * @Date 2019-11-23 19:04
 * @Version 1.0
 **/
public class Application3 {
    public static void main(String[] args) {
        /**映射示例2：map映射+flatMap扁平化流*/
        String[] arraysOfWords = {"Apple","Banana","Nuts","Olive"};
        // 使用Arrays的静态方法创建流
        Stream<String> stringStream = Arrays.stream(arraysOfWords);
        // 对每个word映射为String[]
        stringStream.map(word -> word.split(""))
                // flatMap扁平化流，将生成的流组合成一个流
                // 如果使用map(Arrays::stream),则生成由流元素组成的流
                .flatMap(Arrays::stream)
                // 去掉重复
                .distinct()
                // 终端操作，collect方法是收集器
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

}
