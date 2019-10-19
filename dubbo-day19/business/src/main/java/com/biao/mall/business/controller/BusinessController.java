package com.biao.mall.business.controller;

import com.biao.mall.business.service.BusinessService;
import com.biao.mall.common.dto.BusinessDTO;
import com.biao.mall.common.response.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname BusinessController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-07 11:16
 * @Version 1.0
 **/
@RestController
public class BusinessController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessService businessService;

    /**
     * 模拟用户购买商品下单业务逻辑流程
     * @Param:
     * @Return:
     */
    @PostMapping("/buy")
    String handleBusiness(@RequestBody BusinessDTO businessDTO){
        LOGGER.info("请求参数：{}",businessDTO.toString());
        return businessService.handleBusiness(businessDTO);
    }
}
