/**    
 * 文件名：FriendsSign.java    
 *    
 * 版本信息：    
 * 日期：2017-11-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.chat;

import java.util.List;

import com.mas.web.member.controller.dto.BaseDto;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-2  
 */
public class FriendsSignDto extends BaseDto{

    private String mid;
    private String friendId;
    //private List<Sign> signs;
    private List<String> signs;//["jma2","jma3"]
    private String id;
    private String username;
    private String name;
    private List<String> members;
    
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSigns() {
        return signs;
    }

    public void setSigns(List<String> signs) {
        this.signs = signs;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    
}