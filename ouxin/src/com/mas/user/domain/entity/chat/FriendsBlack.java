/**    
 * 文件名：FriendsBlack.java    
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
 * 类名称： 黑名单 
 * 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class FriendsBlack extends Entity {

	private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_FRIENDS_BLACK";
    
	private Object targetId;
	
	private Object memberId;
	/**
	 * 操作值，0:取消黑名单，1:加入黑名单
	 */
	private Integer type;
	
	private String targetAccount;
	
	private String memberAccount;

	public Object getTargetId() {
		return targetId;
	}

	public void setTargetId(Object targetId) {
		this.targetId = targetId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

	
}
