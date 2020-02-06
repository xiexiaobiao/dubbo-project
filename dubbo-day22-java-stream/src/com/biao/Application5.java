package com.biao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @Classname Application
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-11-23 19:04
 * @Version 1.0
 **/
public class Application5 {
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
        int totalPrice = fruitList.stream()
                .filter(fruit -> "Japan".equals(fruit.getOrigin()))
                //映射转换为Integer流
                .map(Fruit::getPrice)
                //reduce归约计算
                // 也可使用reduce(0,(a,b) -> a+b);
                .reduce(0,Integer::sum);
        System.out.println(totalPrice);

        /** reduce无初始值的归约计算 */
        Optional<Integer> totalPrice2 = fruitList.stream()
                .map(Fruit::getPrice)
                .reduce((a,b) -> a+b);
        // ifPresent，值存在则执行操作，否则 do nothing！
        totalPrice2.ifPresent(System.out::println);

        /** reduce计算最大*/
        Optional<Integer> maxPrice = fruitList.stream()
                .map(Fruit::getPrice)
                // 归约计算最大值：
                // 这里也可以使用reduce((x,y) -> x>y?x:y)
                .reduce(Integer::max);
        // ifPresent，值存在则执行操作，否则 do nothing！
        maxPrice.ifPresent(System.out::println);

        /** reduce计算最小值*/
        Optional<Integer> minPrice = fruitList.stream()
                .map(Fruit::getPrice)
                // 归约计算最小值：也可以使用reduce((x,y) -> x<y?x:y)
                .reduce(Integer::min);
        // ifPresent，值存在则执行操作，否则 do nothing！
        minPrice.ifPresent(System.out::println);
    }

}
