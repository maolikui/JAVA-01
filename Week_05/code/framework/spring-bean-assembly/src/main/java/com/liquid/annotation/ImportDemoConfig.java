package com.liquid.annotation;

import com.liquid.entity.ImportBeanDefinitionRegistrarDemo;
import com.liquid.entity.ImportDemo;
import com.liquid.entity.ImportSelectorDemo;
import com.liquid.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * ImportDemoConfig配置类
 *
 * @author Liquid
 */
@Import({ImportDemo.class, ImportSelectorDemo.class, ImportBeanDefinitionRegistrarDemo.class})
public class ImportDemoConfig {
    @Bean
    public Student student007() {
        return new Student(700, "007");
    }

}
