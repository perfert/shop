/**    
 * 文件名：ApiEntity.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.security.util.verify;

/**
 * 
 * 项目名称：ouxin 类名称： 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public abstract class ApiEntity {
    private String nonce_str;

    private String sign;

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}