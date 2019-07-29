package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Evaluate {
    /*评价编号*/
    private int evaluateId;
    public int getEvaluateId() {
        return evaluateId;
    }
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    /*商品名称*/
    private ProductInfo productObj;
    public ProductInfo getProductObj() {
        return productObj;
    }
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }

    /*用户名*/
    private MemberInfo memberObj;
    public MemberInfo getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }

    /*评价内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*评价时间*/
    private String evaluateTime;
    public String getEvaluateTime() {
        return evaluateTime;
    }
    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

}