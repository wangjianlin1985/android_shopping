package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private OrderInfo orderObj;
    public OrderInfo getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }

    /*��Ʒ����*/
    private ProductInfo productObj;
    public ProductInfo getProductObj() {
        return productObj;
    }
    public void setProductObj(ProductInfo productObj) {
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