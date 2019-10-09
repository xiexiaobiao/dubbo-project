package com.biao.mall.storage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.common.enums.RspStatusEnum;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.storage.dao.ProductDao;
import com.biao.mall.storage.entity.ProductEntity;
import com.biao.mall.storage.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-10-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Override
    public ObjectResponse decreaseStorage(CommodityDTO commodityDTO) {
        int storage = baseMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        ObjectResponse<Object> response = new ObjectResponse<>();
        if (storage > 0){
            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        }

        response.setStatus(RspStatusEnum.FAIL.getCode());
        response.setMessage(RspStatusEnum.FAIL.getMessage());
        return response;
    }

/*    private ProductDao productDao;
    public void test(CommodityDTO commodityDTO){
        QueryWrapper qw = new QueryWrapper();
        qw.eq(true,"commodity_code",commodityDTO.getCommodityCode());
        ProductEntity productEntity = new ProductEntity();
        productDao.updateById(productEntity);
    }*/

}
