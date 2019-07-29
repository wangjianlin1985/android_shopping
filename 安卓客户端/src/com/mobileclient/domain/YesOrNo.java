package com.mobileclient.domain;

import java.io.Serializable;

public class YesOrNo implements Serializable {
    /*是否编号*/
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /*是否信息*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}