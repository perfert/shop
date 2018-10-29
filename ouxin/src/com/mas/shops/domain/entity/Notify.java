/**    
 * 文件名：Order.java    
 *    
 * 版本信息：    
 * 日期：2018-4-19    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称：回调记录 创建人：yixuan
 * 
 * @version v1.00 2018-4-19
 */
public class Notify extends Entity {

    private static final long serialVersionUID = 5288894067481538529L;

    public static final String TABLE_NAME = "SH_NOTIFY";

    private String parameters;

    private Integer typeId;

    private Integer status;
    
    private Object objectId;

    public enum Status {
        CREATE(0, "创建"), SUCCESS(1, "接收成功"), FAIL(2, "接收失败");
        private int status;

        private String comment;

        Status(int status, String comment) {
            this.status =status;
            this.comment = comment;
        }

        public int getStatus() {
            return status;
        }

        public String getComment() {
            return this.comment;
        }

    }
    
    public enum Type {
        DEPOSIT(1, "支付押金"), DEPOSIT_REFUND(2, "退还押金"), ORDER_PAY(3, "订单支付"), ORDER_REFUND(4, "订单退款"), TRADE_SUCCESS( 5, "交易成功");
        private int type;

        private String comment;

        Type(int type, String comment) {
            this.type = type;
            this.comment = comment;
        }

        public int getType() {
            return type;
        }

        public String getComment() {
            return this.comment;
        }

    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getObjectId() {
        return objectId;
    }

    public void setObjectId(Object objectId) {
        this.objectId = objectId;
    }

}
