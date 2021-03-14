package com.liquid.hmily.order.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Order status enum.
 *
 * @author Liquid
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    /**
     * Not pay order status enum.
     */
    NOT_PAY((short) 1, "未支付"),

    /**
     * Paying order status enum.
     */
    PAYING((short) 2, "支付中"),

    /**
     * Pay fail order status enum.
     */
    PAY_FAIL((short) 3, "支付失败"),

    /**
     * Pay success order status enum.
     */
    PAY_SUCCESS((short) 4, "支付成功");

    private final short code;

    private final String desc;

}