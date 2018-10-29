/**    
 * 文件名：Msg.java    
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
 * 类名称： 聊天信息
 * 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class Msg extends Entity {

	private static final long serialVersionUID = 1L;
	
    public static final String TABLE_NAME = "U_MSG";
    
    public static final String MSG_IMAGE_PATH_PREFIX = "msg";

	private Integer type;// 消息类型,(0文本消息,1图片,2语音,3视频,4地理位置,5文件,100 自定义消息类型)
	private String content;
	private String uri;
	private String thumb;
	private String fileName;
	private Double lat;
	private Double lng;
	private String address;
	private Integer height;
	private Integer width;
	private Integer length;
	
	private String fromAccount;
    private String toAccount;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    
}
