package com.biao.mall.business.service;

import com.biao.mall.common.dto.BusinessDTO;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.dubbo.OrderDubboService;
import com.biao.mall.common.dubbo.StorageDubboService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Classname BusinessServiceImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-07 11:18
 * @Version 1.0
 **/
@Service
public class BusinessServiceImpl implements BusinessService {

    @Reference(version = "1.0.0")
    private StorageDubboService storageDubboService;

    @Reference(version = "1.0.0")
    private OrderDubboService orderDubboService;

    private boolean flag;

    @Override
    @GlobalTransactional(timeoutMills = 30000,name = "dubbo-seata-tcc-springboot")
    public String handleBusiness(BusinessDTO businessDTO) {
        System.out.println("handleBusiness >> 开始全局事务，XID = " + RootContext.getXID());
        //1,减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        System.out.println(commodityDTO.toString());
        //开启 TCC RPC 分支之一
        System.out.println("storageDubboService is: " + storageDubboService);
//        Assert.isNull(storageDubboService,"storageDubboService is not null");
        boolean result = storageDubboService.prepareStorage(null,commodityDTO);
        //2,创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        System.out.println(orderDTO.toString());
        //开启 TCC RPC 分支之二
        boolean result2 =  orderDubboService.prepareOrder(null,orderDTO);

        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
//        if (storageResponse.getStatus() != 200 || response.getStatus() != 200) {
//            throw new DefaultException(RspStatusEnum.FAIL);
//        }

//        objectResponse.setStatus(RspStatusEnum.SUCCESS.getCode());
//        objectResponse.setMessage(RspStatusEnum.SUCCESS.getMessage());
//        objectResponse.setData(response.getData());
        return RootContext.getXID();
    }

    public void setStorageDubboService(StorageDubboService storageDubboService) {
        this.storageDubboService = storageDubboService;
    }

    public void setOrderDubboService(OrderDubboService orderDubboService) {
        this.orderDubboService = orderDubboService;
    }
}
