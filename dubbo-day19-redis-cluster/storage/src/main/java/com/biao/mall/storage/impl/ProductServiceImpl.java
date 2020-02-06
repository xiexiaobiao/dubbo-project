package com.biao.mall.storage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dto.CommodityDTO;
import com.biao.mall.storage.dao.ProductDao;
import com.biao.mall.storage.entity.ProductEntity;
import com.biao.mall.storage.service.ProductService;
import io.seata.rm.tcc.api.BusinessActionContext;
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

/*    @Override
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
    }*/

    @Override
    public boolean prepare(BusinessActionContext actionContext, CommodityDTO commodityDTO) {
        System.out.println("actionContext获取Xid prepare>>> "+actionContext.getXid());
        System.out.println("actionContext获取TCC参数 prepare>>> "+actionContext.getActionContext("commodityDTO"));
        int storage = baseMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        if (storage > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        System.out.println("actionContext获取Xid commit>>> "+actionContext.getXid());
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        System.out.println("actionContext获取Xid rollback>>> "+actionContext.getXid());
        CommodityDTO commodityDTO = (CommodityDTO) actionContext.getActionContext("commodityDTO");
        int storage = baseMapper.increaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        if (storage > 0){
            return true;
        }
        return false;
    }

}
