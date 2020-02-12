package com.biao.mall.logistic.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biao.mall.common.component.ResEntity;
import com.biao.mall.common.constant.ResConstant;
import com.biao.mall.common.service.DubboDeliveryService;
import com.biao.mall.logistic.schedule.QuartzService;
import com.biao.mall.logistic.util.SentinelRuleUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 快递物流表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2019-08-15
 */
@RestController
public class DubboDeliveryController {

    static {
        //从Nacos动态加载规则,static标签，可以让规则尽快加载
        SentinelRuleUtil.loadFlowRules();
    }

    private QuartzService quartzService;

    private DubboDeliveryService deliveryService;

    @Autowired
    public DubboDeliveryController(DubboDeliveryService deliveryService,QuartzService quartzService) {
        this.deliveryService = deliveryService;
        this.quartzService = quartzService;
    }

    @SentinelResource(value = "saveOneDelivery")
    @PostMapping("/delivery/one")
    public ResEntity<String> saveOneDelivery(@RequestBody String jsonString) throws BlockException {
//        Method method = this.getClazz().getMethod("saveOneDelivery");
//        String resourceName = "saveOneDelivery";
//        try(Entry entry = SphU.entry(resourceName)){
            JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
            String orderId = jsonObject.getString("orderId");
            //保存待发一个物流单
            deliveryService.saveLogisticSheet(orderId);
            //响应封装
            ResEntity<String> resEntity = new ResEntity<>();
            resEntity.setCode(ResConstant.SUCCESS_CODE);
            resEntity.setMsg(ResConstant.SUCCESS_STRING);
            resEntity.setData("delivery received.");
            return resEntity;
        }

    @SentinelResource(value = "TestResource")
    @GetMapping("/delivery/test")
    public String test( )   {
        return "OK!!";
    }

    @RequestMapping("/delivery/start")
    public String start() throws SchedulerException {
        quartzService.startScheduleJobs();
        return "startScheduleJobs success";
    }

    @RequestMapping("/delivery/stop")
    public String stop(){
        quartzService.stopScheduleJobs();
        return "stopScheduleJobs success";
    }

    @RequestMapping("/delivery/add")
    public String addJob(){
        quartzService.addJobandReplace();
        return "addJobandReplace success";
    }

    @RequestMapping("/delivery/pause")
    public String pauseJob(){
        quartzService.pauseScheduler();
        return "pauseScheduler success";
    }

    @RequestMapping("/delivery/resume")
    public String resumeJob(){
        quartzService.resumeJobs();
        return "resumeJobs success";
    }
}

