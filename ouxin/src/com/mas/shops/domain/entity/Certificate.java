/**    
 * 文件名：Certificate.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.entity;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：企业认证    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
public class Certificate extends Entity {


    private static final long serialVersionUID = 3634925714323230237L;
    
    public static final String TABLE_NAME = "SH_CERTIFICATE";

    private String name; //公司名称
    
    private String businessScope;//营业范围
    
    private String legalPerson;//法人
    
    private String cardNo;//法人身份证号码
    
    private String cardFacePath;
    
    private String cardReversePath;
    
    private String businessLicense; //营业执照
    
    private String businessLicensePath; 
    
    private String organizationCode; //组织机构代码
    
    private Object shopsId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Object getShopsId() {
        return shopsId;
    }

    public void setShopsId(Object shopsId) {
        this.shopsId = shopsId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
    
    
}
