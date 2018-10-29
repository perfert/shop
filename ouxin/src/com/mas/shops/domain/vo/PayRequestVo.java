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
import java.util.List;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-4-25  
 */
public class PayRequestVo {

    private String orderId;
    private String pkStoreId;
    private BigDecimal totalPrice;//人民币价格
    private String name;
    
    private List<WealthTypeVo> rateList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPkStoreId() {
        return pkStoreId;
    }

    public void setPkStoreId(String pkStoreId) {
        this.pkStoreId = pkStoreId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<WealthTypeVo> getRateList() {
        return rateList;
    }

    public void setRateList(List<WealthTypeVo> rateList) {
        this.rateList = rateList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
