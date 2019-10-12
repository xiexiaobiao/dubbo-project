package com.biao.mall.business.service;

import com.biao.mall.common.dto.BusinessDTO;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.dubbo.OrderDubboService;
import com.biao.mall.common.dubbo.StorageDubboService;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.exception.DefaultException;
import com.biao.mall.common.response.ObjectResponse;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

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
    @GlobalTransactional(timeoutMills = 30000,name = "dubbo-seata-at-springboot")
    public ObjectResponse handleBusiness(BusinessDTO businessDTO) {
        System.out.println("开始全局事务，XID = " + RootContext.getXID());
        ObjectResponse<Object> objectResponse = new ObjectResponse<>();
        //1,减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        ObjectResponse storageResponse = storageDubboService.decreaseStorage(commodityDTO);
        //2,创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        ObjectResponse<OrderDTO> response = orderDubboService.createOrder(orderDTO);

        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
        if (storageResponse.getStatus() != 200 || response.getStatus() != 200) {
            throw new DefaultException(RspStatusEnum.FAIL);
        }

        objectResponse.setStatus(RspStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RspStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(response.getData());
        return objectResponse;
    }
}
