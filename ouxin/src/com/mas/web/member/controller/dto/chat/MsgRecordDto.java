/**    
 * 文件名：MsgRecordDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-15    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.chat;

import com.mas.web.member.controller.dto.BaseDto;

/**
 *  类名称：聊天记录DTO
 *  创建人：yixuan
 * 
 * @version v1.00 2017-11-15
 */
public class MsgRecordDto extends BaseDto{
    private String sendId;

    private String targetId;

    private String token;
    
    private String fileType;

    private Integer chatType;

    private Integer msgType;

    private String content;

    private String fileName;

    private String uri;

    private String thumb;

    private Integer length;

    private String address;

    private Double lat;

    private Double lng;

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    

}
