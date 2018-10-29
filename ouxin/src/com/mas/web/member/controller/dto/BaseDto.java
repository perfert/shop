/**    
 * 文件名：BaseDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto;

import java.util.List;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-1  
 */
public class BaseDto {
    
    private String lang = "";
    private String mid;
    private List<String> deletFilePath;
    
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getDeletFilePath() {
        return deletFilePath;
    }

    public void setDeletFilePath(List<String> deletFilePath) {
        this.deletFilePath = deletFilePath;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    
}
