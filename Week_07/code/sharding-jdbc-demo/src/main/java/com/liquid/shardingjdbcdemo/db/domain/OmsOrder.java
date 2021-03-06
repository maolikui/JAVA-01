package com.liquid.shardingjdbcdemo.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class OmsOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table oms_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table oms_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.order_sn
     *
     * @mbg.generated
     */
    private String orderSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.order_status
     *
     * @mbg.generated
     */
    private Short orderStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.consignee
     *
     * @mbg.generated
     */
    private String consignee;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.mobile
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.address
     *
     * @mbg.generated
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.goods_price
     *
     * @mbg.generated
     */
    private BigDecimal goodsPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.freight_price
     *
     * @mbg.generated
     */
    private BigDecimal freightPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.ship_sn
     *
     * @mbg.generated
     */
    private String shipSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.ship_channel
     *
     * @mbg.generated
     */
    private String shipChannel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.ship_time
     *
     * @mbg.generated
     */
    private LocalDateTime shipTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.refund_amount
     *
     * @mbg.generated
     */
    private BigDecimal refundAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.refund_type
     *
     * @mbg.generated
     */
    private String refundType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.refund_content
     *
     * @mbg.generated
     */
    private String refundContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.refund_time
     *
     * @mbg.generated
     */
    private LocalDateTime refundTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.confirm_time
     *
     * @mbg.generated
     */
    private LocalDateTime confirmTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.end_time
     *
     * @mbg.generated
     */
    private LocalDateTime endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.add_time
     *
     * @mbg.generated
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oms_order.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.id
     *
     * @return the value of oms_order.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.id
     *
     * @param id the value for oms_order.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.user_id
     *
     * @return the value of oms_order.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.user_id
     *
     * @param userId the value for oms_order.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.order_sn
     *
     * @return the value of oms_order.order_sn
     *
     * @mbg.generated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.order_sn
     *
     * @param orderSn the value for oms_order.order_sn
     *
     * @mbg.generated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.order_status
     *
     * @return the value of oms_order.order_status
     *
     * @mbg.generated
     */
    public Short getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.order_status
     *
     * @param orderStatus the value for oms_order.order_status
     *
     * @mbg.generated
     */
    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.consignee
     *
     * @return the value of oms_order.consignee
     *
     * @mbg.generated
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.consignee
     *
     * @param consignee the value for oms_order.consignee
     *
     * @mbg.generated
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.mobile
     *
     * @return the value of oms_order.mobile
     *
     * @mbg.generated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.mobile
     *
     * @param mobile the value for oms_order.mobile
     *
     * @mbg.generated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.address
     *
     * @return the value of oms_order.address
     *
     * @mbg.generated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.address
     *
     * @param address the value for oms_order.address
     *
     * @mbg.generated
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.goods_price
     *
     * @return the value of oms_order.goods_price
     *
     * @mbg.generated
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.goods_price
     *
     * @param goodsPrice the value for oms_order.goods_price
     *
     * @mbg.generated
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.freight_price
     *
     * @return the value of oms_order.freight_price
     *
     * @mbg.generated
     */
    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.freight_price
     *
     * @param freightPrice the value for oms_order.freight_price
     *
     * @mbg.generated
     */
    public void setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.ship_sn
     *
     * @return the value of oms_order.ship_sn
     *
     * @mbg.generated
     */
    public String getShipSn() {
        return shipSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.ship_sn
     *
     * @param shipSn the value for oms_order.ship_sn
     *
     * @mbg.generated
     */
    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.ship_channel
     *
     * @return the value of oms_order.ship_channel
     *
     * @mbg.generated
     */
    public String getShipChannel() {
        return shipChannel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.ship_channel
     *
     * @param shipChannel the value for oms_order.ship_channel
     *
     * @mbg.generated
     */
    public void setShipChannel(String shipChannel) {
        this.shipChannel = shipChannel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.ship_time
     *
     * @return the value of oms_order.ship_time
     *
     * @mbg.generated
     */
    public LocalDateTime getShipTime() {
        return shipTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.ship_time
     *
     * @param shipTime the value for oms_order.ship_time
     *
     * @mbg.generated
     */
    public void setShipTime(LocalDateTime shipTime) {
        this.shipTime = shipTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.refund_amount
     *
     * @return the value of oms_order.refund_amount
     *
     * @mbg.generated
     */
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.refund_amount
     *
     * @param refundAmount the value for oms_order.refund_amount
     *
     * @mbg.generated
     */
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.refund_type
     *
     * @return the value of oms_order.refund_type
     *
     * @mbg.generated
     */
    public String getRefundType() {
        return refundType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.refund_type
     *
     * @param refundType the value for oms_order.refund_type
     *
     * @mbg.generated
     */
    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.refund_content
     *
     * @return the value of oms_order.refund_content
     *
     * @mbg.generated
     */
    public String getRefundContent() {
        return refundContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.refund_content
     *
     * @param refundContent the value for oms_order.refund_content
     *
     * @mbg.generated
     */
    public void setRefundContent(String refundContent) {
        this.refundContent = refundContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.refund_time
     *
     * @return the value of oms_order.refund_time
     *
     * @mbg.generated
     */
    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.refund_time
     *
     * @param refundTime the value for oms_order.refund_time
     *
     * @mbg.generated
     */
    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.confirm_time
     *
     * @return the value of oms_order.confirm_time
     *
     * @mbg.generated
     */
    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.confirm_time
     *
     * @param confirmTime the value for oms_order.confirm_time
     *
     * @mbg.generated
     */
    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.end_time
     *
     * @return the value of oms_order.end_time
     *
     * @mbg.generated
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.end_time
     *
     * @param endTime the value for oms_order.end_time
     *
     * @mbg.generated
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.add_time
     *
     * @return the value of oms_order.add_time
     *
     * @mbg.generated
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.add_time
     *
     * @param addTime the value for oms_order.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.update_time
     *
     * @return the value of oms_order.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.update_time
     *
     * @param updateTime the value for oms_order.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oms_order.deleted
     *
     * @return the value of oms_order.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oms_order.deleted
     *
     * @param deleted the value for oms_order.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_order
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", consignee=").append(consignee);
        sb.append(", mobile=").append(mobile);
        sb.append(", address=").append(address);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", freightPrice=").append(freightPrice);
        sb.append(", shipSn=").append(shipSn);
        sb.append(", shipChannel=").append(shipChannel);
        sb.append(", shipTime=").append(shipTime);
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", refundType=").append(refundType);
        sb.append(", refundContent=").append(refundContent);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", confirmTime=").append(confirmTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_order
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OmsOrder other = (OmsOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getConsignee() == null ? other.getConsignee() == null : this.getConsignee().equals(other.getConsignee()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getGoodsPrice() == null ? other.getGoodsPrice() == null : this.getGoodsPrice().equals(other.getGoodsPrice()))
            && (this.getFreightPrice() == null ? other.getFreightPrice() == null : this.getFreightPrice().equals(other.getFreightPrice()))
            && (this.getShipSn() == null ? other.getShipSn() == null : this.getShipSn().equals(other.getShipSn()))
            && (this.getShipChannel() == null ? other.getShipChannel() == null : this.getShipChannel().equals(other.getShipChannel()))
            && (this.getShipTime() == null ? other.getShipTime() == null : this.getShipTime().equals(other.getShipTime()))
            && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
            && (this.getRefundType() == null ? other.getRefundType() == null : this.getRefundType().equals(other.getRefundType()))
            && (this.getRefundContent() == null ? other.getRefundContent() == null : this.getRefundContent().equals(other.getRefundContent()))
            && (this.getRefundTime() == null ? other.getRefundTime() == null : this.getRefundTime().equals(other.getRefundTime()))
            && (this.getConfirmTime() == null ? other.getConfirmTime() == null : this.getConfirmTime().equals(other.getConfirmTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_order
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getConsignee() == null) ? 0 : getConsignee().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getGoodsPrice() == null) ? 0 : getGoodsPrice().hashCode());
        result = prime * result + ((getFreightPrice() == null) ? 0 : getFreightPrice().hashCode());
        result = prime * result + ((getShipSn() == null) ? 0 : getShipSn().hashCode());
        result = prime * result + ((getShipChannel() == null) ? 0 : getShipChannel().hashCode());
        result = prime * result + ((getShipTime() == null) ? 0 : getShipTime().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getRefundType() == null) ? 0 : getRefundType().hashCode());
        result = prime * result + ((getRefundContent() == null) ? 0 : getRefundContent().hashCode());
        result = prime * result + ((getRefundTime() == null) ? 0 : getRefundTime().hashCode());
        result = prime * result + ((getConfirmTime() == null) ? 0 : getConfirmTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table oms_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table oms_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        userId("user_id", "userId", "INTEGER", false),
        orderSn("order_sn", "orderSn", "VARCHAR", false),
        orderStatus("order_status", "orderStatus", "SMALLINT", false),
        consignee("consignee", "consignee", "VARCHAR", false),
        mobile("mobile", "mobile", "VARCHAR", false),
        address("address", "address", "VARCHAR", false),
        goodsPrice("goods_price", "goodsPrice", "DECIMAL", false),
        freightPrice("freight_price", "freightPrice", "DECIMAL", false),
        shipSn("ship_sn", "shipSn", "VARCHAR", false),
        shipChannel("ship_channel", "shipChannel", "VARCHAR", false),
        shipTime("ship_time", "shipTime", "TIMESTAMP", false),
        refundAmount("refund_amount", "refundAmount", "DECIMAL", false),
        refundType("refund_type", "refundType", "VARCHAR", false),
        refundContent("refund_content", "refundContent", "VARCHAR", false),
        refundTime("refund_time", "refundTime", "TIMESTAMP", false),
        confirmTime("confirm_time", "confirmTime", "TIMESTAMP", false),
        endTime("end_time", "endTime", "TIMESTAMP", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table oms_order
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}