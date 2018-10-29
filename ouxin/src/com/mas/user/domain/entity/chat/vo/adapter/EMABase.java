/**    
 * 文件名：EMABase.java    
 *    
 * 版本信息：    
 * 日期：2016-12-22    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.chat.vo.adapter;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public class EMABase {
    long nativeHandler = 0L;

    public EMABase() {
    }

    public boolean equals(Object var1) {
        return var1 != null && var1 instanceof EMABase && this.nativeHandler == ((EMABase)var1).nativeHandler;
    }
}