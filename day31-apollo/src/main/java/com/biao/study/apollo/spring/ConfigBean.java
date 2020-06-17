package com.biao.study.apollo.spring;

import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName ConfigBean
 * @Description: TODO
 * @Author Biao
 * @Date 2020/6/17
 * @Version V1.0
 **/
public class ConfigBean {

    private String school;

    @Value("${timeValue:100}")
    private String timeValue;

    public String getSchool() {
        return school;
    }

    @Value("${school:200}")
    public void setSchool(String school) {
        this.school = school;
    }

    public String getTime() {
        return timeValue;
    }

    public void setTime(String timeValue) {
        this.timeValue = timeValue;
    }
}
