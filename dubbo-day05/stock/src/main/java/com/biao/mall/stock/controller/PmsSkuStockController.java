package com.biao.mall.stock.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * sku的库存 前端控制器
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-06
 */
@Controller
@RequestMapping("/stock/")
public class PmsSkuStockController {

    @RequestMapping("/rmq")
    public void handleMq(){

    }
}

