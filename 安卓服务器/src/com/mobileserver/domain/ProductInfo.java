package com.mobileserver.domain;

public class ProductInfo {
    /*��Ʒ���*/
    private String productNo;
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /*��Ʒ���*/
    private int productClassObj;
    public int getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(int productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*��Ʒ����*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*��ƷͼƬ*/
    private String productPhoto;
    public String getProductPhoto() {
        return productPhoto;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    /*��Ʒ����*/
    private float productPrice;
    public float getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    /*��Ʒ���*/
    private int productCount;
    public int getProductCount() {
        return productCount;
    }
    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    /*�Ƿ��Ƽ�*/
    private String recommendFlag;
    public String getRecommendFlag() {
        return recommendFlag;
    }
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    /*����ֵ*/
    private int hotNum;
    public int getHotNum() {
        return hotNum;
    }
    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    /*�ϼ�����*/
    private java.sql.Timestamp onlineDate;
    public java.sql.Timestamp getOnlineDate() {
        return onlineDate;
    }
    public void setOnlineDate(java.sql.Timestamp onlineDate) {
        this.onlineDate = onlineDate;
    }

}