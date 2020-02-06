package com.biao.lambda;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Classname IBufferedReaderProcess
 * @Description 函数式接口定义
 * @Author xiexiaobiao
 * @Date 2019-11-24 18:16
 * @Version 1.0
 **/
@FunctionalInterface
public interface IBufferedReaderProcess {
    String process(BufferedReader br) throws IOException;
}
