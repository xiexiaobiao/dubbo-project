package com.biao.mall;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * @Classname ApplicationMain
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-11-01 22:24
 * @Version 1.0
 **/
@RestController
@SpringBootApplication
//@Slf4j
public class ApplicationMain {

    private final Logger log = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class,args);
        System.out.println("ELK Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            log.info("log from ELK app time: {}",System.currentTimeMillis());
        }
        return "ELK test success";
    }

    @RequestMapping("/api")
    public String APItest() throws InterruptedException, IOException {
        /** scheme 选项 http/tcp
         * 1. java客户端的方式是以tcp协议在9300端口上进行通信
         * 2. http客户端的方式是以http协议在9200端口上进行通信
         */
        RestHighLevelClient client = new RestHighLevelClient(
                //builder可以继续添加多个HttpHost
                RestClient.builder(
                        new HttpHost("192.168.1.204", 9200, "http")));

         /** 有四种不同的方式来产生JSON格式的文档（document）
            .Manually (aka do it yourself) using native byte[] or as a String
            .Using a Map that will be automatically converted to its JSON equivalent
            .Using a third party library to serialize your beans such as Jackson
            .Using built-in helpers XContentFactory.jsonBuilder()
         */
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "biao");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        String index = "my_temp_index";
        IndexRequest indexRequest = new IndexRequest(index)
                .id("1")
                .timeout(TimeValue.timeValueSeconds(1))
                .setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
                .opType(DocWriteRequest.OpType.INDEX)
                .source(builder);

        //Synchronous execution
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());

        //asynchronous execution,
        // client.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);

        client.close();
        return "ELK API test success";
    }

    ActionListener listener = new ActionListener() {
        @Override
        public void onResponse(Object o) {
            System.out.println("ELK API ASYN test success");
        }

        @Override
        public void onFailure(Exception e) {
            System.out.println("ELK API ASYN test failed");
        }
    };

}
