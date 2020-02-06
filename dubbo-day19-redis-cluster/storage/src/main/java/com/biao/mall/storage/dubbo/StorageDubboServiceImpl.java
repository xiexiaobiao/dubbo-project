package com.biao.mall.storage.dubbo;

import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.dubbo.StorageDubboService;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.storage.service.ProductService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname StorageDubboServiceImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-06 13:10
 * @Version 1.0
 **/
@Service(version = "1.0.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}")
@org.springframework.stereotype.Service
public class StorageDubboServiceImpl implements StorageDubboService {

    @Autowired
    private ProductService  productService;

    @Override
    public boolean prepareStorage(BusinessActionContext actionContext, CommodityDTO commodityDTO) {
        System.out.println("prepareStorage invoked!");
        System.out.println("prepareStorage 全局事务id ：" + RootContext.getXID());
        System.out.println("prepareStorage actionContext获取Xid  >>> "+actionContext.getXid());
        ObjectResponse<Object> response = new ObjectResponse<>();
        //prepare方法开启TCC模式
        /**这里使用了dubboservice中取得ObjectResponse的模式，*/
//        BusinessActionContext businessActionContext = new BusinessActionContext();
        // 进入本地TCC模式
        productService.prepare(null,commodityDTO);
        return true;
    }

    @Override
    public boolean commitStorage(BusinessActionContext actionContext) {
        System.out.println("commitStorage actionContext获取Xid >>> "+actionContext.getXid());
        return true;
    }

    @Override
    public boolean rollbackStorage(BusinessActionContext actionContext) {
        System.out.println("rollbackStorage actionContext获取Xid >>> "+actionContext.getXid());
        return false;
    }
}
