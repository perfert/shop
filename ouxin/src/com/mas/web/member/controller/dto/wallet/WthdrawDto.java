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
public class WthdrawDto extends BaseDto {

    private Integer pagesize;

    private Integer pagenum;
    
    private String num;
    
    private String address;
    
    private String payPassword;
    
    private String wealthTypeId;
    
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
