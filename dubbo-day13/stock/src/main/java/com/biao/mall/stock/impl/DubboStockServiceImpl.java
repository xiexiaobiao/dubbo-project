package com.biao.mall.stock.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.mall.common.dao.DubboStockDao;
import com.biao.mall.common.entity.DubboStockEntity;
import com.biao.mall.common.service.DubboStockService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexiaobiao
 * @since 2019-08-12
 */
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = DubboStockService.class,version = "1.0.0") //dubbo 服务注解
@org.springframework.stereotype.Service //spring 服务注解
public class DubboStockServiceImpl extends ServiceImpl<DubboStockDao, DubboStockEntity> implements DubboStockService {

    private DubboStockDao dubboStockDao;

    @Autowired
    public DubboStockServiceImpl(DubboStockDao dubboStockDao){
        this.dubboStockDao = dubboStockDao;
    }

    /**
    * @Description: 查询所有库存
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-12 22:15
    */
    @Override
    public List<DubboStockEntity> listStock() {
        QueryWrapper qw = new QueryWrapper();
        qw.isNotNull("id");
        return dubboStockDao.selectList(qw);
    }

    @Override
    @Transactional
    public int updateStockRPC(DubboStockEntity stockEntity) throws BlockException {
        String resourceName = "updateStockRPC";
        this.initWhiteRules(resourceName);
        //获取app来源
        ContextUtil.enter(resourceName, "mall-business");
        Entry entry = null;
        try {
            entry = SphU.entry(resourceName);
            if (Objects.equals(null, stockEntity)) {
                return -1;
            }
            return dubboStockDao.updateById(stockEntity);
        } catch (BlockException ex) {

        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
        return -1;
    }

    /**
    * @Description: 更新单个库存
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-12 22:21
    */
    @Override
    @Transactional
    public int updateStock(DubboStockEntity stockEntity, QueryWrapper qw){
        //这里务必确保qw非null，否则语句也可以自行，但会update全表！！！
        if (Objects.equals(qw,null)){
            return -1;
        }
        return dubboStockDao.update(stockEntity,qw);
    }

    /**
    * @Description: 插入单个库存
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-12 22:23
    */
    @Override
    @Transactional
    public int saveStock(DubboStockEntity stockEntity){
       return dubboStockDao.insert(stockEntity);
    }

    /**
    * @Description: 根据item-id查找库存条目，这里没考虑一个商品存放多个地方，或分品牌等情况，故是唯一的
    * @param item_id
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-14 20:16
    */
    @Override
    public DubboStockEntity getStockEntity(String item_id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq(true,"item_id",item_id);
        return dubboStockDao.selectOne(qw);
    }

    /**白名单规则*/
    private void initWhiteRules(String resourceName){
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(resourceName);
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appA,appE");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }
}
