/**    
 * 文件名：Apk.java    
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
 * APK
 * 
 * @author yixuan
 * @since 2016-12-29
 */
public class Apk extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "APP_APK";

    public static final String DOWN_LOAD = "ouxin";
    
    private String versionName; // 版本

    private double version; // 版本号

    private String detail; // 更新说明

    private String filePath; // 路径

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
