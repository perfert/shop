/**    
 * 文件名：WealthTypeVo.java    
 *    
 * 版本信息：    
 * 日期：2018-4-25    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.vo;

import java.math.BigDecimal;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2018-4-25
 */
public class WealthTypeVo {

    private String name;

    private String id;

    private String type;

    private BigDecimal value;

    private double rate;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
