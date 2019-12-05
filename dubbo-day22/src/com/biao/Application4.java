package com.biao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname Application
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-11-23 19:04
 * @Version 1.0
 **/
public class Application4 {
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
        // 注意这里每个都要重建一个流
        // 是否全部价格大于50
        boolean almach = fruitList.stream().allMatch(fruit -> fruit.getPrice() > 50);
        System.out.println(almach);
        // 是否至少有一种产自America
        boolean anyMatch = fruitList.stream().anyMatch(fruit -> "America".equals(fruit.getOrigin()));
        System.out.println(anyMatch);
        // 找出流中第3个，
        Optional<Fruit> thirdOne = fruitList.stream().skip(2).findFirst();
        // 存在则打印，防止NPE
        thirdOne.ifPresent(System.out::println);
        // 找出流中任意一个，，
        Optional<Fruit> anyOne = fruitList.stream().findAny();
        // ifPresent，值存在则执行操作，否则 do nothing！
        anyOne.ifPresent(System.out::println);
    }

}
