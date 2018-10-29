/**    
 * 文件名：StatisSend.java    
 *    
 * 版本信息：    
 * 日期：2017-12-7    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-7
 */
public class StatisSendVo {

    private String sendMoney;

    private String nickName;

    private Long sendCount;

    private String avatar;

    public String getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(String sendMoney) {
        this.sendMoney = sendMoney;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getSendCount() {
        return sendCount;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
