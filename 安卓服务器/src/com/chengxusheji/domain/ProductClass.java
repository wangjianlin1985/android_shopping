package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ProductClass {
    /*类别编号*/
    private int classId;
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /*类别名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

}