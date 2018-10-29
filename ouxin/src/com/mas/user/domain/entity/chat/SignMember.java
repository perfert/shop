/**    
 * 文件名：Sign.java    
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
 * 项目名称：chat 类名称： 好友标签表 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class SignMember extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_SIGN_MEMBER";

    private String username;
    

    private Object memberId;

    private Object signId;
    
    //view端展示
    private String nickName;
    
    private String avatar;
    
    private String alias;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public Object getSignId() {
        return signId;
    }

    public void setSignId(Object signId) {
        this.signId = signId;
    }

    
}
