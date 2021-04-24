package com.liquid.kafkademo.model;

import lombok.Data;

import java.util.Date;

/**
 * Msg
 *
 * @author Liquid
 */
@Data
public class Message {
    private Long id;
    private String msg;
    private Date sendTime;
}
