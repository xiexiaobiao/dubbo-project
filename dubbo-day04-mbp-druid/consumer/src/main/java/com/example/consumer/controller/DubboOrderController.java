package com.example.consumer.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.example.common.constant.AnnotationConstants;
import com.example.common.order.entity.DubboOrderEntity;
import com.example.common.order.service.DubboOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaobiao
 * @since 2019-08-04
 */
@RestController
@RequestMapping("/order")
public class DubboOrderController {

    private final static Logger logger = LoggerFactory.getLogger(DubboOrderController.class);

    @Reference(version = AnnotationConstants.VERSION)
    private DubboOrderService dubboOrderService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<DubboOrderEntity> listOrder(){
        logger.info("web response handler.. ");
        List<DubboOrderEntity> list =  dubboOrderService.listAllOrder();
        System.out.println("list.size :: "+list.size());
        logger.info(list.get(0).toString());
        logger.info(list.get(1).toString());
       return list;
    }
}

