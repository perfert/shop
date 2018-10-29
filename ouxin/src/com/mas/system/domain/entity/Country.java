package com.mas.system.domain.entity;

import com.mas.core.domain.entity.Entity;
/**
 * 国家
 * @author yixuan
 * @since 2016-12-29
 */
public class Country extends Entity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "SYS_COUNTRY";

    private String code;

    private String name;
    
    private String cnName;
    
    private String lang;
    
    private String iso;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
    
    
}