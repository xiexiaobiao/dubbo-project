package com.biao;

/**
 * @Classname Fruit
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-11-23 18:54
 * @Version 1.0
 **/
public class Fruit {
    private String name;
    private String origin;
    private Integer price;

    public Fruit(String name, String origin, int price) {
        this.name = name;
        this.origin = origin;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                ", price=" + price +
                '}';
    }
}
