/**    
 * 文件名：RedpacketDto.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.wallet;

import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-2
 */
public class PayPasswordtDto extends BaseDto {

    private String phone;

    private String code;

    private String captcha;

    private String key;
    
    private String payPassword;

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
    
    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

}
