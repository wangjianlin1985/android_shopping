package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private MemberInfo memberObj;
    public MemberInfo getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
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

    /*商品数量*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}