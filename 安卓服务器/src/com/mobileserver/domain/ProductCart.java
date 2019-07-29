package com.mobileserver.domain;

public class ProductCart {
    /*记录编号*/
    private int cartId;
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    /*用户名*/
    private String memberObj;
    public String getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(String memberObj) {
        this.memberObj = memberObj;
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

    /*商品数量*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}