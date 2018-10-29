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
 * 类名称：好友申请DTO    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-2  
 */
public class ApplyDto extends BaseDto{

    private String mid;
    private String friendId;
    private String message;
    
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

    
}
