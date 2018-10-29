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

import java.util.List;

import com.mas.user.domain.entity.wallet.WealthType;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-4-11  
 */
public class ThirdPayVo {

    private int isHasPwd;
    private List<WealthType> typeList;
    
    public int getIsHasPwd() {
        return isHasPwd;
    }
    public void setIsHasPwd(int isHasPwd) {
        this.isHasPwd = isHasPwd;
    }
    public List<WealthType> getTypeList() {
        return typeList;
    }
    public void setTypeList(List<WealthType> typeList) {
        this.typeList = typeList;
    }
    
    
    
}
