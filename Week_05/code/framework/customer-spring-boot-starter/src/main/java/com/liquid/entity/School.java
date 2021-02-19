package com.liquid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * School实体类
 *
 * @author Liquid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    private Klass classx;
    private List<Student> studentx;

    public void ding() {
        System.out.println("Classx have " + this.classx.getStudents().size() + " students and one is " + this.studentx.get(0));
    }
}
