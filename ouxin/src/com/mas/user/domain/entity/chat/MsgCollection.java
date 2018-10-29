/**    
 * 文件名：Msg.java    
 *    
 * 版本信息：    
 * 日期：2017-1-14    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.chat;

import com.mas.core.domain.entity.Entity;

/**
 * 后期可改成KEY VALUE形式 
 * 项目名称：chat 
 * 类名称：收藏Entity
 * 收藏 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class MsgCollection extends Entity {

    private static final long serialVersionUID = 1L;

    public static String TABLE_NAME = "U_MSG_COLLECTION";

    public static final String MSG_IMAGE_PATH_PREFIX = "collecion";
    
    public enum Type {
        TXT(0,""),
        IMAGE(1,""),
        VOICE(2,""),
        VIDEO(3,""),
        LOCATION(4,""),
        FILE(5,""),
        DEFINE(6,"");

        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private Type(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }
    
    
    // 收藏类型,(0文本消息,1图片,2语音,3视频,4地理位置,5文件,100 自定义消息类型)
    private Integer type;

    private Object memberId;

    private Object coverMemberId;

    private String content;

    private String imageUrl;

    private String videoUrl;

    private String thumb;
    
    private Long videoTime;

    private Long voiceTime;
    
    private String voiceUrl;

    private String fileUrl;

    private String fileSize;

    private Double lat;

    private Double lng;

    private String address;
    
    private String locationUrl;

    //view
    private String nick;
    private String avatar;
    
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

    public Object getCoverMemberId() {
        return coverMemberId;
    }

    public void setCoverMemberId(Object coverMemberId) {
        this.coverMemberId = coverMemberId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    
    public Long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(Long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public Long getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Long videoTime) {
        this.videoTime = videoTime;
    }
   
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

}

