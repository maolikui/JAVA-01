package com.liquid.aopdefination;

import com.liquid.aopdefination.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Demo component类
 *
 * @author Liquid
 */
@Component
public class DemoComponent {
    @Autowired
    private DemoService demoService;

    public void demo() {
        demoService.demo("动态代理生成的对象执行demo方法");
        demoService.toString();
    }
}
