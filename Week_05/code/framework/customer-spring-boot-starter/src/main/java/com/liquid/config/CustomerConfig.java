package com.liquid.config;

import com.liquid.entity.Klass;
import com.liquid.entity.School;
import com.liquid.entity.Student;
import com.liquid.properties.CustomerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * 自定义Config
 * 业务功能的Bean在此处进行配置
 *
 * @author Liquid
 */
@Configuration
@EnableConfigurationProperties({CustomerProperties.class})
@ConditionalOnProperty(prefix = "customer", name = "enable", havingValue = "true")
public class CustomerConfig {
    @Autowired
    CustomerProperties customerProperties;

    @Bean
    public Student student200() {
        return new Student(200, "kk200");
    }

    @Bean
    public Klass class2() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(300, "kk300"));
        students.add(new Student(400, "kk400"));
        return new Klass(customerProperties.getChineseTeacher(), students);
    }

    @Bean
    public School school() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(500, "kk500"));
        students.add(new Student(600, "kk600"));
        return new School(new Klass(customerProperties.getChineseTeacher(), students), students);
    }
}
