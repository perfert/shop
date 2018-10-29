/**    
 * 文件名：FriendsApply.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.chat;

import com.mas.core.domain.entity.Entity;

/**
 * 
 * 项目名称：chat 
 * 类名称： 好友申请表
 * 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class FriendsApply extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_FRIENDS_APPLY";

    public static final Integer TYPE_ADD_FRIEND=0;
    public static final Integer TYPE_AGREE_FRIEND=1;
    public static final Integer TYPE_REFUSE_FRIEND=2;
    public static final Integer TYPE_DELETE_FRIEND=3;
    
    private Object applyId;

    private Object receiveId;

    private Integer type;

    private String msg;
    
    private String applyAccount;
    
    private String receiveAccount;
    
    //view
    private String applyNickName;
    private String receiveNickName;
    private String applyAvatar;
    private String receiveAvatar;
    
    public Object getApplyId() {
        return applyId;
    }

    public void setApplyId(Object applyId) {
        this.applyId = applyId;
    }

    public Object getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Object receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getApplyNickName() {
        return applyNickName;
    }

    public void setApplyNickName(String applyNickName) {
        this.applyNickName = applyNickName;
    }

    public String getReceiveNickName() {
        return receiveNickName;
    }

    public void setReceiveNickName(String receiveNickName) {
        this.receiveNickName = receiveNickName;
    }

    public String getApplyAccount() {
        return applyAccount;
    }

    public void setApplyAccount(String applyAccount) {
        this.applyAccount = applyAccount;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public String getApplyAvatar() {
        return applyAvatar;
    }

    public void setApplyAvatar(String applyAvatar) {
        this.applyAvatar = applyAvatar;
    }

    public String getReceiveAvatar() {
        return receiveAvatar;
    }

    public void setReceiveAvatar(String receiveAvatar) {
        this.receiveAvatar = receiveAvatar;
    }

    
}
