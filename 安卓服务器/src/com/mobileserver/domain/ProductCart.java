package com.mobileserver.domain;

public class ProductCart {
    /*��¼���*/
    private int cartId;
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    /*�û���*/
    private String memberObj;
    public String getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(String memberObj) {
        this.memberObj = memberObj;
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

    /*��Ʒ����*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}