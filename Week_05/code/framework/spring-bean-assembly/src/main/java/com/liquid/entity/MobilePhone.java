package com.liquid.entity;

/**
 * Mobile类
 *
 * @Liquid
 */
public class MobilePhone {
    private String brand;

    public MobilePhone() {
    }

    public MobilePhone(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
