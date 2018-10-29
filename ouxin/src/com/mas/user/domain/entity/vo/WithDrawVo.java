/**    
 * 文件名：WithDrawVo.java    
 *    
 * 版本信息：    
 * 日期：2017-12-28    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

import com.mas.user.domain.entity.wallet.Withdraw;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-28
 */
public class WithDrawVo extends Withdraw {

    private static final long serialVersionUID = 8687565868777429354L;

    private String myAddress;//鸥信请求地址

    private String code;

    private String account;

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
