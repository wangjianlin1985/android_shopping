package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private MemberInfo memberObj;
    public MemberInfo getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
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

    /*��Ʒ����*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}