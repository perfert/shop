package com.mas.user.domain.entity.chat;

import com.mas.core.domain.entity.Entity;

/**
 * 好友表
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 * 
 */
public class Friends extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_FRIENDS";

    private Object memberId;

    private String memberAccount;
    
    private Object friendId;
    
    private String friendAccount;

    private Object singId;

    private String alias;
    
    private String phone;
    
    private String detail;
    
    private String img;
    
    //view
    private String memberNickName;
    private String friendNickName;
    
    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getFriendId() {
        return friendId;
    }

    public void setFriendId(Object friendId) {
        this.friendId = friendId;
    }

    public Object getSingId() {
        return singId;
    }

    public void setSingId(Object singId) {
        this.singId = singId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    
}
