package com.biao.mall.stock.util;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Properties;

/**
 * @Classname SentinelRuleConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-14 12:19
 * @Version 1.0
 **/
public class SentinelRuleUtil {
    // nacos server ip
    private static final String remoteAddress = "${spring.cloud.sentinel.datasource.flow.nacos.server-addr}";
    // nacos group
    private static final String groupId = "${spring.cloud.sentinel.datasource.flow.nacos.groupId}";
    // nacos dataId
    private static final String dataId = "${spring.cloud.sentinel.datasource.flow.nacos.dataId}";
    // if change to true, should be config NACOS_NAMESPACE_ID
    private static boolean isDemoNamespace = false;
    // fill your namespace id,if you want to use namespace.
    // for example: 0f5c7314-4983-4022-ad5a-347de1d1057d,you can get it on nacos's console
    private static final String NACOS_NAMESPACE_ID = "0f5c7314-4983-4022";

    public static void loadFlowRules(){
        // public NacosDataSource(final String serverAddr, final String groupId, final String dataId,
        //                           Converter<String, T> parser)
        ReadableDataSource<String, List<FlowRule>> flowDataSource = new NacosDataSource<>(remoteAddress,groupId,dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>(){
                }));
        FlowRuleManager.register2Property(flowDataSource.getProperty());
    }

    private static void loadMyNamespaceRules() {
        Properties properties = new Properties();
        properties.put("serverAddr",remoteAddress);
        properties.put("namespace",NACOS_NAMESPACE_ID);
        //NacosDataSource(final Properties properties, final String groupId, final String dataId,
        //                           Converter<String, T> parser)
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties,groupId,dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }


}
