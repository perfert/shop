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

import java.math.BigDecimal;

import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-2
 */
public class RedpacketDto extends BaseDto {

    //红包ID
    private String rpId;
    
    private String wealthTypeId;
    
    //接红包ID
    private String receiveId;
    
    private String groupId;
    
    private int redPacketType;
    //总红包数量,对于群
    private int number;
    
    private BigDecimal money;

    private String summary;

    private String envelopeName;

    private Integer pagesize;

    private Integer pagenum;
    
    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEnvelopeName() {
        return envelopeName;
    }

    public void setEnvelopeName(String envelopeName) {
        this.envelopeName = envelopeName;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getRedPacketType() {
        return redPacketType;
    }

    public void setRedPacketType(int redPacketType) {
        this.redPacketType = redPacketType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(String wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }
    

}
