package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Evaluate {
    /*���۱��*/
    private int evaluateId;
    public int getEvaluateId() {
        return evaluateId;
    }
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    /*��Ʒ����*/
    private ProductInfo productObj;
    public ProductInfo getProductObj() {
        return productObj;
    }
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }

    /*�û���*/
    private MemberInfo memberObj;
    public MemberInfo getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*����ʱ��*/
    private String evaluateTime;
    public String getEvaluateTime() {
        return evaluateTime;
    }
    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

}