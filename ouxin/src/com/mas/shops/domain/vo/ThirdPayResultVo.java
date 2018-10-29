/**    
 * 文件名：ＷalletPayVo.java    
 *    
 * 版本信息：    
 * 日期：2018-4-11    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.vo;


/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-4-11  
 */
public class ThirdPayResultVo {

    private int code;
    private Object orderId;
    private Object guaranteeId;
    private Object recordId;
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(Object guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public Object getRecordId() {
        return recordId;
    }

    public void setRecordId(Object recordId) {
        this.recordId = recordId;
    }
    
    
    
    
    
}
