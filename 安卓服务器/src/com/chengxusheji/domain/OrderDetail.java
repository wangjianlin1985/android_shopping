package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderDetail {
    /*记录编号*/
    private int detailId;
    public int getDetailId() {
        return detailId;
    }
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    /*定单编号*/
    private OrderInfo orderObj;
    public OrderInfo getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }

    /*商品名称*/
    private ProductInfo productObj;
    public ProductInfo getProductObj() {
        return productObj;
    }
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }

    /*商品单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*订购数量*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}