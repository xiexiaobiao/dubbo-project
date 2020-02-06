package com.biao.mall.business.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SentinelTestController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-11 21:30
 * @Version 1.0
 **/
@RestController
public class SentinelTestController {

    private String resourceName = "testSentinel";

    @GetMapping("/testSentinel")
    public String testSentinel(){
        initFlowQpsRule();
        Entry entry = null;
        String retVal;
        try{
            entry = SphU.entry(resourceName,EntryType.IN);
            retVal = "passed";
        }catch(BlockException e){
            retVal="block";
        }finally{
            if (entry != null){
                entry.exit();
            }
        }
        return retVal;
    }

    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(resourceName);
        // set limit qps to 2
        rule1.setCount(2);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

}
