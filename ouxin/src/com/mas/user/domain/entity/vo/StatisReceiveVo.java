/**    
 * 文件名：StatisReceiveVo.java    
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
public class StatisReceiveVo {

    private Long receiveLuckCount;

    private Long receiveCount;

    private String receiveMoney;

    private String nickName;

    private String avatar;

    public Long getReceiveLuckCount() {
        return receiveLuckCount;
    }

    public void setReceiveLuckCount(Long receiveLuckCount) {
        this.receiveLuckCount = receiveLuckCount;
    }

    public Long getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(Long receiveCount) {
        this.receiveCount = receiveCount;
    }

    public String getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(String receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
