/**    
 * 文件名：Shops.java    
 *    
 * 版本信息：    
 * 日期：2018-3-22    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.entity;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：商家
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-22  
 */
public class Shops extends Entity {
    
    private static final long serialVersionUID = 8070285671419794451L;
    
    public static final String TABLE_NAME = "SH_SHOPS";
    
    public static final String SHOPS_IMAGE_PATH_PREFIX = "shops";
    
    private String title;  //店铺标题
    private String legalPerson;//法人
    private String cardNo;
    private String mobile;   //管理员手机号码
    private String address;  //店铺地址
    private String link;
    private Integer storeStatus;
    private Integer authStatus; //0:未审核,1:通过,2:审核失败
    
    private Object memberId;
    private Object thirdShopsId;
    
    private String logo;
    private String detail;// 企业简介
    private Integer storeCategory;  //主体类型,企业，个人
    private String manageName; //公众帐号管理员的姓名
    private String phone;    //店铺联系电话
    private String reason;
    private String name; //公司名称
    private String cardFacePath;
    private String cardReversePath;
    private String businessLicensePath; 
    //View
    private Integer attention;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(Integer storeCategory) {
        this.storeCategory = storeCategory;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Integer storeStatus) {
        this.storeStatus = storeStatus;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBusinessLicensePath() {
        return businessLicensePath;
    }

    public void setBusinessLicensePath(String businessLicensePath) {
        this.businessLicensePath = businessLicensePath;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Object getThirdShopsId() {
        return thirdShopsId;
    }

    public void setThirdShopsId(Object thirdShopsId) {
        this.thirdShopsId = thirdShopsId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    
} 

