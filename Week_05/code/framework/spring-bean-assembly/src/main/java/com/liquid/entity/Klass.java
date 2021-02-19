package com.liquid.entity;


import java.util.List;

/**
 * Klass实体类
 *
 * @author Liquid
 */
public class Klass {
    private List<Student> students;

    public Klass() {
    }

    public Klass(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
