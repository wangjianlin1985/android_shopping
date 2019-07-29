package com.mobileserver.domain;

public class ProductInfo {
    /*商品编号*/
    private String productNo;
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /*商品类别*/
    private int productClassObj;
    public int getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(int productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*商品名称*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*商品图片*/
    private String productPhoto;
    public String getProductPhoto() {
        return productPhoto;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    /*商品单价*/
    private float productPrice;
    public float getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    /*商品库存*/
    private int productCount;
    public int getProductCount() {
        return productCount;
    }
    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    /*是否推荐*/
    private String recommendFlag;
    public String getRecommendFlag() {
        return recommendFlag;
    }
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    /*人气值*/
    private int hotNum;
    public int getHotNum() {
        return hotNum;
    }
    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    /*上架日期*/
    private java.sql.Timestamp onlineDate;
    public java.sql.Timestamp getOnlineDate() {
        return onlineDate;
    }
    public void setOnlineDate(java.sql.Timestamp onlineDate) {
        this.onlineDate = onlineDate;
    }

}