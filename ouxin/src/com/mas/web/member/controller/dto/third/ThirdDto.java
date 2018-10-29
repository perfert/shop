/**    
 * 文件名：LoginDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.third;

import com.mas.shops.domain.vo.WealthTypeVo;
import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称：第三方DTO 创建人：yixuan
 * 
 * @version v1.00 2018-4-11
 */
public class ThirdDto extends BaseDto {

    private String mid;

    private String token;

    private String wealthTypeId;

    private String payPassword;

    private String thirdOrderId;

    private String shopsId;

    private String cash;

    private String name;

    private String sn;
    
    //店铺信息
    private String title;  //店铺标题
    
    private String mobile;   //管理员手机号码
    
    private String address;  //店铺地址
    
    private String link;
    
    private String logoPath;
    
    private String description;
    
    private String legalPerson;//法人
    
    private String cardNo;//法人身份证号码
    
    //支付信息
    private WealthTypeVo[] types;
    
    //
    private String payData;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(String wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public WealthTypeVo[] getTypes() {
        return types;
    }

    public void setTypes(WealthTypeVo[] types) {
        this.types = types;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPayData() {
        return payData;
    }

    public void setPayData(String payData) {
        this.payData = payData;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
