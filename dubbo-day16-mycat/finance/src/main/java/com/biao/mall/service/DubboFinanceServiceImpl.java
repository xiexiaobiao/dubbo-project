package com.biao.mall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dao.DubboFinanceDao;
import com.biao.mall.common.entity.DubboFinanceEntity;
import com.biao.mall.common.service.DubboFinanceService;
import com.biao.mall.common.service.RocketMQService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行账户表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-09-01
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0",group = "mall")
public class DubboFinanceServiceImpl extends ServiceImpl<DubboFinanceDao, DubboFinanceEntity> implements DubboFinanceService {
        private RocketMQService rocketMQService;
        private DubboFinanceDao financeDao;

        @Autowired
        public DubboFinanceServiceImpl(RocketMQService rocketMQService,DubboFinanceDao financeDao){
            this.rocketMQService = rocketMQService;
            this.financeDao = financeDao;
        }

        public boolean updateFinance() throws MQClientException {
            rocketMQService.consumeConcurrentMsg();
            return true;
        }

        //插入1000条数据，看data分布
        @Override
        public String testMycat(){
            DubboFinanceEntity financeEntity = new DubboFinanceEntity();
            for (int i = 0; i < 1000; i++) {
                financeEntity.setUserId(String.valueOf(i+100));
                financeDao.insert(financeEntity);
            }
            return "testMycat successfully";
        }

}
