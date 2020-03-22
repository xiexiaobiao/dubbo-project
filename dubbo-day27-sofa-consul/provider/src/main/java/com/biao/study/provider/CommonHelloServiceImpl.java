package com.biao.study.provider;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.biao.study.common.api.CommonHelloService;
import org.springframework.stereotype.Component;

/**
 * @ClassName AnnotationServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
@SofaService(interfaceType = CommonHelloService.class, bindings = { @SofaServiceBinding(bindingType = "bolt") })
@Component
public class CommonHelloServiceImpl implements CommonHelloService {
    @Override
    public String sayAnnotation(String string) {
        return "hello -- " + string;
    }
}
