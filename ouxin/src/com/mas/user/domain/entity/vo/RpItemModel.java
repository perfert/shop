/**    
 * 文件名：RpItemModel.java    
 *    
 * 版本信息：    
 * 日期：2017-12-5    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-5
 */
public class RpItemModel {

    private String userId;

    private String nickname;

    private String rpId;

    private String cash; // 领取的钱

    private int type;

    private String time;

    private int isLuck;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsLuck() {
        return isLuck;
    }

    public void setIsLuck(int isLuck) {
        this.isLuck = isLuck;
    }

}
