package com.mobileserver.domain;

public class OrderInfo {
    /*�������*/
    private String orderNo;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /*�µ���Ա*/
    private String memberObj;
    public String getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(String memberObj) {
        this.memberObj = memberObj;
    }

    /*�µ�ʱ��*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*�����ܽ��*/
    private float totalMoney;
    public float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*����״̬*/
    private int orderStateObj;
    public int getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(int orderStateObj) {
        this.orderStateObj = orderStateObj;
    }

    /*���ʽ*/
    private String buyWay;
    public String getBuyWay() {
        return buyWay;
    }
    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    /*�ջ�������*/
    private String realName;
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /*�ջ��˵绰*/
    private String telphone;
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /*��������*/
    private String postcode;
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /*�ջ���ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*������Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}