package com.liquid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Student 实体类
 *
 * @author Liquid
 */
@Data
@AllArgsConstructor
@ToString
public class Student implements Serializable {
    private int id;
    private String name;
}
