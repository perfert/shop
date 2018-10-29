/**    
 * 文件名：ShopsDto.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.web.manage.controller.dto.shops;

import java.util.List;

import com.mas.shops.domain.entity.MortagageConfig;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
public class MortagageConfigDto extends BaseCtrDto<MortagageConfig>{
    
    private List<WealthType> typeList;

    public List<WealthType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<WealthType> typeList) {
        this.typeList = typeList;
    }
    
    
}
