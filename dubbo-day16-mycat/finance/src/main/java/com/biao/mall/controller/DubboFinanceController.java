package com.biao.mall.controller;


import com.biao.mall.common.service.DubboFinanceService;
import com.biao.mall.service.DubboFinanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 银行账户表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-09-01
 */
@RestController
@RequestMapping("/finance")
public class DubboFinanceController {

    private DubboFinanceServiceImpl  financeService;

    @Autowired
    public DubboFinanceController(DubboFinanceServiceImpl  financeService) {
        this.financeService = financeService;
    }

    @RequestMapping("/mycat")
    public String testMycat(){
        return financeService.testMycat();
    }

}

