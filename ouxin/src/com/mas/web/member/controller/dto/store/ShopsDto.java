/**    
 * 文件名：LoginDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.store;

import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称：登录DTO 创建人：yixuan
 * 
 * @version v1.00 2017-11-1
 */
public class ShopsDto extends BaseDto {

    private String name; //公司名称
    
    private String businessLicensePath; 
    
    private String legalPerson;//法人
    
    private String cardFacePath;
    
    private String cardReversePath;
    
    private String cardNo;//法人身份证号码
    
    //店铺信息
    private String title;  //店铺标题
    
    private String manageName; //公众帐号管理员的姓名

    private String mobile;   //管理员手机号码
    
    private String address;  //店铺地址
    
    private Integer storeCategory;
    
    private String shopsId;
    private String query;
    private Integer pagesize;
    private Integer pagenum;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessLicensePath() {
        return businessLicensePath;
    }

    public void setBusinessLicensePath(String businessLicensePath) {
        this.businessLicensePath = businessLicensePath;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCardFacePath() {
        return cardFacePath;
    }

    public void setCardFacePath(String cardFacePath) {
        this.cardFacePath = cardFacePath;
    }

    public String getCardReversePath() {
        return cardReversePath;
    }

    public void setCardReversePath(String cardReversePath) {
        this.cardReversePath = cardReversePath;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
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

    public Integer getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(Integer storeCategory) {
        this.storeCategory = storeCategory;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    
}
