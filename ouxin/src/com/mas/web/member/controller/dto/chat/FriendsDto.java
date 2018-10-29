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

import com.mas.web.member.controller.dto.BaseDto;

/**    
 * 类名称：登录DTO    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-1  
 */
public class FriendsDto extends BaseDto{

    private String mid;
    private String friendId;
    private String phone;
    
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
