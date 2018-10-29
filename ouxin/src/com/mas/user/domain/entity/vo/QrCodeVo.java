/**    
 * 文件名：QrCodeVo.java    
 *    
 * 版本信息：    
 * 日期：2017-12-19    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-19
 */
public class QrCodeVo {

    private String id;// 地址ID

    private String name;

    private String realAddress;

    private double totalIn; // 币量

    private String qrcode; // URL

    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(String realAddress) {
        this.realAddress = realAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public double getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(double totalIn) {
        this.totalIn = totalIn;
    }

}
