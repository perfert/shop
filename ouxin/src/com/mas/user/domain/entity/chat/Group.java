/**    
 * 文件名：Group.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.chat;

import com.alibaba.fastjson.JSONObject;
import com.mas.core.domain.entity.Entity;

/**
 * 
 * 项目名称：chat 类名称： 群聊 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class Group extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_GROUP";

    private String pushGroupId;
    
    private String name;    //群组头像和群初始名称都是群成员信息的拼接,这种方式是为了更快更方便的解析.----->群成员之间不是都有好友关系,本地没有非好友的信息存储

    private String avatar;
    
    private String detail;

    private Boolean open; // 群聊天室类型（0：私有；1：公开）

    private Boolean allow;// 群成员能否邀请别人入群（0：否；1：是）

    private Long maxusers;

    private Integer nowCount;
    
    private Object owner;
    
    private String ownerAccount;
    
    public Group(){}

    /**
     * 
     * 创建一个新的实例 Group.    
     *    
     * @param name   组名
     * @param desc   组描述
     * @param open   是否公开
     * @param allow  群成员能否邀请别人入群
     * @param maxusers 最大组员
     * @param nowCount 当前人数
     * @param owner    拥有者
     * @param ownerAccount
     */
    public Group(String name, String desc, Boolean open, Boolean allow, Long maxusers, Integer nowCount,Object owner, String ownerAccount) {
        super();
        this.name = name;
        this.detail = desc;
        this.open = open;
        this.allow = allow;
        this.maxusers = maxusers;
        this.nowCount = nowCount;
        this.owner = owner;
        this.ownerAccount = ownerAccount;
    }
    
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getAllow() {
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    public Long getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Long maxusers) {
        this.maxusers = maxusers;
    }

    public Integer getNowCount() {
        return nowCount;
    }

    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getPushGroupId() {
        return pushGroupId;
    }

    public void setPushGroupId(String pushGroupId) {
        this.pushGroupId = pushGroupId;
    }

    public String getRealName() {
        JSONObject jsonObject = JSONObject.parseObject(getName());
        return jsonObject.getString("groupname");
    }

}
