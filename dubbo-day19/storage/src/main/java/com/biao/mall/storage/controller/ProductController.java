package com.biao.mall.storage.controller;


import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.storage.service.ProductService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService  productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/dec")
    @GlobalTransactional
    public String handleBusiness(@RequestBody CommodityDTO commodityDTO) {
        LOGGER.info("请求参数：{}", commodityDTO.toString());
        LOGGER.info("全局XID：{}", RootContext.getXID());
        ObjectResponse<Object> response = new ObjectResponse<>();

         if (productService.prepare(null,commodityDTO)){
             return "success";
         };
         return null;
    }


}

