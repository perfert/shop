package com.mas.web.manage.controller.dto.app;

import com.mas.user.domain.entity.app.Apk;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * ArticleCtrDto  controller DTO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author YIXUAN
 */
public class ApkCtrDto extends BaseCtrDto<Apk>{
    
    private Double nowVersion;

    public Double getNowVersion() {
        return nowVersion;
    }

    public void setNowVersion(Double nowVersion) {
        this.nowVersion = nowVersion;
    }
    
    
}