/**    
 * 文件名：SmsConfig.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.domain.entity;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
public class SmsConfig extends Entity {
    
    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "SYS_SMS_CONFIG";
    
    private String name;
    
    private Boolean isDefault;
    
    private String account;

    private String password;     //密钥
  
    private String nonceString; //随机串

    private String smsApi;      //接口地址

    private String smsModuleCode;//+86
    
    private String messageApi;
    
    private String messageModuleCode;

    private Integer smsInvalid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNonceString() {
        return nonceString;
    }

    public void setNonceString(String nonceString) {
        this.nonceString = nonceString;
    }

    public String getSmsApi() {
        return smsApi;
    }

    public void setSmsApi(String smsApi) {
        this.smsApi = smsApi;
    }

    public String getSmsModuleCode() {
        return smsModuleCode;
    }

    public void setSmsModuleCode(String smsModuleCode) {
        this.smsModuleCode = smsModuleCode;
    }

    public Integer getSmsInvalid() {
        return smsInvalid;
    }

    public void setSmsInvalid(Integer smsInvalid) {
        this.smsInvalid = smsInvalid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getMessageApi() {
        return messageApi;
    }

    public void setMessageApi(String messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessageModuleCode() {
        return messageModuleCode;
    }

    public void setMessageModuleCode(String messageModuleCode) {
        this.messageModuleCode = messageModuleCode;
    }
    
    
}