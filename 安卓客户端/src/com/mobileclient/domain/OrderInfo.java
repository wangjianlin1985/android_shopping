package com.mobileclient.domain;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    /*订单编号*/
    private String orderNo;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /*下单会员*/
    private String memberObj;
    public String getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(String memberObj) {
        this.memberObj = memberObj;
    }

    /*下单时间*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*订单总金额*/
    private float totalMoney;
    public float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*订单状态*/
    private int orderStateObj;
    public int getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(int orderStateObj) {
        this.orderStateObj = orderStateObj;
    }

    /*付款方式*/
    private String buyWay;
    public String getBuyWay() {
        return buyWay;
    }
    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    /*收货人姓名*/
    private String realName;
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /*收货人电话*/
    private String telphone;
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /*邮政编码*/
    private String postcode;
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /*收货地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*附加信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}