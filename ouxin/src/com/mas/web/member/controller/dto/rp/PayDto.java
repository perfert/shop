/**    
 * 文件名：RedpacketDto.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.rp;


import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-2
 */
public class PayDto extends BaseDto {

    //红包ID
    private String rpId;
    
    private String payPassword;
    
    private String wealthTypeId;

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(String wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }
    
    
}
