package com.biao.mall.common.service;

import com.biao.mall.common.entity.DubboFinanceEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行账户表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-09-01
 */
@Service
public interface DubboFinanceService extends IService<DubboFinanceEntity> {

    String testMycat();
}
