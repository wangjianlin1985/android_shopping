package com.mobileserver.domain;

public class OrderDetail {
    /*��¼���*/
    private int detailId;
    public int getDetailId() {
        return detailId;
    }
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    /*�������*/
    private String orderObj;
    public String getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(String orderObj) {
        this.orderObj = orderObj;
    }

    /*��Ʒ����*/
    private String productObj;
    public String getProductObj() {
        return productObj;
    }
    public void setProductObj(String productObj) {
        this.productObj = productObj;
    }

    /*��Ʒ����*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��������*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}