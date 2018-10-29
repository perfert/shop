/**    
 * 文件名：SMSDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.common;

import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称： 找回密码Dto 
 * 创建人：yixuan
 * 
 * @version v1.00 2017-11-22
 */
public class PwdDto extends BaseDto {

    private String phone;

    private String code;

    private String captcha;

    private String key;
    
    private String password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
