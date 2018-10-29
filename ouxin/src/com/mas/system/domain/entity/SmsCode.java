/**    
 * 文件名：SmsCode.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称：验证码
 *  创建人：yixuan
 * 
 * 通过session，可以做到自动登录。做自动登录需要将sessionId保存到本地磁盘中（持久化保存）。下载请求后台服务器的时候将sessionid如上代码一起传递就行。但是要注意一个问题就是session也是有有效期的，session的有效期默认是30分钟，30分钟之后需要再次登录
 * @version v1.00 2017-11-22
 */
public class SmsCode extends Entity {

    private static final long serialVersionUID = 7504616571915004392L;
    
    public static final String TABLE_NAME = "SYS_SMS_CODE";
    
    public static final int TYPE_REGISTER = 0;
    public static final int TYPE_FORGET = 1;
    public static final int TYPE_PAY_PASSWORD = 2;
    
    private int type; // 验证码类型; 0:注册,1:忘记密码,2:钱包

    private String mobileCode;

    private String mobile;

    private String code;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
