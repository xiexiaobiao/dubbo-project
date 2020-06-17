package com.biao.study.apollo.api;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @ClassName ApiMain
 * @Description: apollo 使用API方式获取配置，不依赖spring框架 ，使用时去掉注释即可
 * @Author Biao
 * @Date 2020/6/17
 * @Version V1.0
 **/
/*
public class ApiMain {

    private static final Logger logger = LoggerFactory.getLogger(ApiMain.class);
    private String DEFAULT_VALUE = "undefined";
    // config instance is singleton for each namespace and is never null
    private Config config;

    ConfigChangeListener changeListener = configChangeEvent -> {
        for (String key: configChangeEvent.changedKeys()
             ) {
            ConfigChange change = configChangeEvent.getChange(key);
            logger.info("Config Change >>>>> key: {}, oldValue: {}, newValue: {}, changeType: {}",
                    change.getPropertyName(), change.getOldValue(), change.getNewValue(),
                    change.getChangeType());
        }
    };

    public ApiMain() {
        this.config = ConfigService.getAppConfig();
        this.config.addChangeListener(changeListener);
    }

    public static void main(String[] args) throws IOException {
        ApiMain apiMain = new ApiMain();
        // getConfig("key"); key为配置数据中的key
        apiMain.getConfig("name");
    }

    private String getConfig(String key){
        String result = config.getProperty(key,DEFAULT_VALUE);
        logger.info(String.format("Loading key >>>> %s with value: %s", key, result));
        return result;
    }
}*/
