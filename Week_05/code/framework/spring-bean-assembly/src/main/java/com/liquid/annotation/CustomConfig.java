package com.liquid.annotation;

import com.liquid.entity.Klass;
import com.liquid.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * 自定义配置,Java中显示配置
 *
 * @author Liquid
 */
@ComponentScan("com.liquid")
@Configuration
public class CustomConfig {
    @Bean
    public Student student200() {
        return new Student(200, "kk200");
    }

    @Bean
    public Klass class2() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(300, "kk300"));
        students.add(new Student(400, "kk400"));
        return new Klass(students);
    }

    @Bean
    public Klass class3() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(500, "kk500"));
        students.add(new Student(600, "kk600"));
        return new Klass(students);
    }

}
