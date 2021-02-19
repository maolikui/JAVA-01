package com.liquid.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * School实体类
 *
 * @author Liquid
 */
@Component
public class School {
    @Autowired
    @Qualifier("class3")
    Klass classx;
    @Resource
    Student student200;

    public Klass getClassx() {
        return classx;
    }

    public void setClassx(Klass classx) {
        this.classx = classx;
    }

    public Student getStudent200() {
        return student200;
    }

    public void setStudent200(Student student200) {
        this.student200 = student200;
    }
}
