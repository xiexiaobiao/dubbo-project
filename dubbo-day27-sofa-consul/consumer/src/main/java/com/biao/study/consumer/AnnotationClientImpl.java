package com.biao.study.consumer;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.biao.study.common.api.CommonHelloService;
import org.springframework.stereotype.Component;

/**
 * @ClassName AnnotationClientImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/22
 * @Version V1.0
 **/
@Component(value = "helloSyncServiceReference")
public class AnnotationClientImpl implements AnnotationClient {

    @SofaReference(interfaceType = CommonHelloService.class, binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CommonHelloService commonHelloService;

    public String sayClientAnnotation(String str) {

        String result = commonHelloService.sayAnnotation(str);

        return result;
    }
}
