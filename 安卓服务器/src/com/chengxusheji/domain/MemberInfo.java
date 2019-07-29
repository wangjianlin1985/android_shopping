package com.chengxusheji.domain;

import java.sql.Timestamp;
public class MemberInfo {
    /*会员用户名*/
    private String memberUserName;
    public String getMemberUserName() {
        return memberUserName;
    }
    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*真实姓名*/
    private String realName;
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    private Timestamp birthday;
    public Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*联系邮箱*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*联系qq*/
    private String qq;
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*会员照片*/
    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}