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
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StorageDubboService storageDubboService;

    @Autowired
    private OrderDubboService orderDubboService;

    private boolean flag;

    @Override
    @GlobalTransactional(timeoutMills = 30000,name = "dubbo-seata-TCC-springboot")
    public ObjectResponse<Object> handleBusiness(BusinessDTO businessDTO) {
        System.out.println("开始全局事务，XID = " + RootContext.getXID());
        ObjectResponse<Object> objectResponse = new ObjectResponse<>();
        //1,第一个事务参与者，减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        /*ObjectResponse<Object> storageResponse = storageDubboService.decreaseStorage(commodityDTO);*/
        /** 注意这里的prepare第一个实参为null，BusinessActionContext对象从seata框架自动获得*/
        boolean result1 = storageDubboService.prepare(null,commodityDTO);
        //2,第二个事务参与者，创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        /*ObjectResponse<OrderDTO> orderResponse = orderDubboService.createOrder(orderDTO);*/
        // BusinessActionContext actionContext =  new BusinessActionContext();
        /** 注意这里的prepare第一个实参为null，BusinessActionContext对象从seata框架自动获得*/
        boolean result2 = orderDubboService.prepare(null,orderDTO);
        ObjectResponse<Object> orderResponse = new ObjectResponse<>();
        if (!result1 || !result2){
            orderResponse.setStatus(RspStatusEnum.FAIL.getCode());
            orderResponse.setMessage(RspStatusEnum.FAIL.getMessage());
            return orderResponse;
        }

        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }

        /*if (storageResponse.getStatus() != 200 || orderResponse.getStatus() != 200) {
            throw new DefaultException(RspStatusEnum.FAIL);
        }*/

        objectResponse.setStatus(RspStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RspStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(orderResponse.getData());
        return objectResponse;
    }
}
