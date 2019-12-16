package com.biao.flink;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Getter
@Setter
public class Vehicle {
    // 类型：car/truck/suv/pickup/other
    private String type;
    // 车牌号：随机6位数字
    private Integer plate;
    // yellow/red/white/black
    private String color;
    // 车重: 1.5t/2.5t
    private Float weight;



    public Vehicle(String type, Integer plate, String color,Float weight) {
        this.type = type;
        this.plate = plate;
        this.color = color;
        this.weight = weight;
    }

    public Vehicle() {
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type='" + type + '\'' +
                ", plate='" + plate + '\'' +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlate() {
        return plate;
    }

    public void setPlate(Integer plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public boolean isEndOfSequence() {
        return false;
    }

    public long getSequenceTimestamp() {
        // UTC的millisecond做eventTime
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)*1000;
    }
}
