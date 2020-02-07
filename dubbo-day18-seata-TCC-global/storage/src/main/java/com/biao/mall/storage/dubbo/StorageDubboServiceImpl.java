package com.biao.mall.storage.dubbo;

import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.dubbo.StorageDubboService;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.storage.service.ProductService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
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
public class StorageDubboServiceImpl implements StorageDubboService {

    @Autowired
    private ProductService  productService;

    @Override
    public ObjectResponse decreaseStorage(CommodityDTO commodityDTO) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        ObjectResponse<Object> response = new ObjectResponse<>();
        //prepare方法开启TCC模式
        /**这里使用了dubboservice中取得ObjectResponse的模式，*/
        BusinessActionContext businessActionContext = new BusinessActionContext();
        if ( productService.prepare(businessActionContext,commodityDTO)){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }
        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }
}
