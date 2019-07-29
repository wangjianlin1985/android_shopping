package com.mobileserver.domain;

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
    private String orderObj;
    public String getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(String orderObj) {
        this.orderObj = orderObj;
    }

    /*商品名称*/
    private String productObj;
    public String getProductObj() {
        return productObj;
    }
    public void setProductObj(String productObj) {
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