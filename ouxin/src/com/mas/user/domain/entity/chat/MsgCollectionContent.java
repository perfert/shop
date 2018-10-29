/**    
 * 文件名：Msg.java    
 *    
 * 版本信息：    
 * 日期：2017-1-21  
 * Copyright 足下 Corporation 2017    
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.chat;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.mas.core.domain.entity.Entity;

/**
 * 
 * 项目名称：chat 类名称： 收藏 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class MsgCollectionContent extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_MSG_COLLECTION_CONTENT";
    
    private Object collectionId;
    private Integer msgType;
    private String content;
    private String uri;     
    private String thumb;
    
    private Double lat;
    private Double lng;
    private String address;
    
    private Integer height; //图高
    private Integer width;  //图宽
    private Integer length; //录音大小

    /** 文件 */
    @Transient
    private MultipartFile file;
    
    @Transient
    private MultipartFile thumbFile;
    
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Object getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Object collectionId) {
        this.collectionId = collectionId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getThumbFile() {
        return thumbFile;
    }

    public void setThumbFile(MultipartFile thumbFile) {
        this.thumbFile = thumbFile;
    }

    
}
