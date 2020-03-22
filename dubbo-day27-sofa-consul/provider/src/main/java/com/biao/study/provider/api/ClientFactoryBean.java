package com.biao.study.provider.api;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName ClientFactoryBean
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
@Component(value = "clientFactory")
public class ClientFactoryBean implements ClientFactoryAware {

    private ClientFactory clientFactory;

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }
}
