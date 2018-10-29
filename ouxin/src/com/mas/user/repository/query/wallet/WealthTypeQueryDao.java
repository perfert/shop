/**    
 * 文件名：WealthQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.wallet;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.wallet.WealthType;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface WealthTypeQueryDao extends QueryDao<WealthType>{

    /**
     * 获取所有财富类型
     * @return
     */
    List<WealthType> getAll();

    List<WealthType> getAllContainWealth(String mid);

    WealthType getWealth(String memberId, String wealthTypeId);

    boolean isEnable(String wealthType);

}
