package com.liquid.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Klass实体类
 *
 * @author Liquid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Klass {
    private String chineseTeacher;
    private List<Student> students;

    public void dong() {
        System.out.println("Klass chineseTeacher: " + chineseTeacher + " students:" + this.getStudents());
    }
}
