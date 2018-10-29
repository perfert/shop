/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.dto.chat;

import com.mas.web.member.controller.dto.BaseDto;


/**
 * 注册DTO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class RegistCtrDto extends BaseDto{
    
    private String userid;
    private String username;
    
    private String account;
    private String code;
    private String captcha;
    private String password;
    private String headImage;
    private String nickname;
    
    
    
    private String wxid;

    private String mobileCaptha;

    private String smsCaptha;

    private Boolean isWeixin;
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileCaptha() {
        return mobileCaptha;
    }

    public void setMobileCaptha(String mobileCaptha) {
        this.mobileCaptha = mobileCaptha;
    }

    public String getSmsCaptha() {
        return smsCaptha;
    }

    public void setSmsCaptha(String smsCaptha) {
        this.smsCaptha = smsCaptha;
    }

    public Boolean getIsWeixin() {
        return isWeixin;
    }

    public void setIsWeixin(Boolean isWeixin) {
        this.isWeixin = isWeixin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    
}