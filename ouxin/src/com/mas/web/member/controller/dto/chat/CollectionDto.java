/**    
 * 文件名：CollectionDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-16    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.chat;

import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-11-16
 */
public class CollectionDto extends BaseDto {

    private String id;
    
    private Integer pagesize;

    private Integer pagenum;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String mid;

    // 收藏对象的用户ID
    private String yid;

    private String content;

    // 图片地址
    private String iamgeUrl;

    private String videoPath;

    private String thumb;

    private Long videoTime;

    private String voicePath;

    private Long voiceTime;

    private String filePath;

    private String fileName;

    private String fileSize;

    private Double lat;

    private Double lng;

    private String address;
    
    private String locationPath;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getYid() {
        return yid;
    }

    public void setYid(String yid) {
        this.yid = yid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Long getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Long videoTime) {
        this.videoTime = videoTime;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public Long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(Long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public String getLocationPath() {
        return locationPath;
    }

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    
}
