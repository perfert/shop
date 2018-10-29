/**    
 * 文件名：GroupMember.java    
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
 * 类名称： 群与用户关联表 
 * 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class GroupMember extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_GROUP_MEMBER";

    private Object memberId;
    
    /**实际为code_account*/
    private String memberAccount;

    private Object groupId;

    private String groupName;
    
    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
