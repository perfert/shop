/**    
 * 文件名：AdPosition.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29   
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.app;

import com.mas.core.domain.entity.Entity;

/**
 * 广告位置
 * 
 * @author yixuan
 * @since 2016-12-29
 */
public class AdPosition extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "APP_AD_POSITION";

    private String name;

    private Integer width;

    private Integer height;

    private String detail;

    private String sign; // 规则标志

    private Integer maxAd; // 允许的最大轮播图数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getMaxAd() {
        return maxAd;
    }

    public void setMaxAd(Integer maxAd) {
        this.maxAd = maxAd;
    }

}
