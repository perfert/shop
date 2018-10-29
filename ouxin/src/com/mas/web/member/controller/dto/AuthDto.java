/**    
 * 文件名：LoginDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-18    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-18  
 */
public class AuthDto {

    //lang=cn&mid=70&nonce_str=b3itNgDN8J7NrfuR8qyFkiKuQw4qcXtb&key=20eb136527514573b9f6c23cd1c69f12
            
    private String mid;
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
    
    
}
