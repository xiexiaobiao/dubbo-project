package com.biao.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Classname Application
 * @Description Lambda示例
 * @Author xiexiaobiao
 * @Date 2019-11-24 18:17
 * @Version 1.0
 **/
public class Application {
    public static void main(String[] args) throws IOException {

        // 使用Lambda具体实现1
        // 也可使用processFile((BufferedReader br) -> br.readLine())；
        String oneLine = processFile(BufferedReader::readLine);
        System.out.println(oneLine);
        // 使用Lambda具体实现2
        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(twoLine);
    }

    private static String processFile(IBufferedReaderProcess processor) throws IOException {
        // try-with-resource语法
        try(BufferedReader br = new BufferedReader(new FileReader("demo.txt"))){
            return processor.process(br);
        }
    }

}
