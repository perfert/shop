/**    
 * 文件名：LoginDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.chat;

import java.util.List;

import com.mas.web.member.controller.dto.BaseDto;

/**    
 * 类名称：群组DTO    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-2  
 */
public class GroupDto extends BaseDto{

    private String mid;
    private String friendId;
    private String message;
    
    //组名
    private String name;
    //会员数组
    private String[] memberIds;
    //组ID
    private String groupId;
    
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String[] memberIds) {
        this.memberIds = memberIds;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    
}
