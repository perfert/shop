/**    
 * 文件名：WealthDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.wallet;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.wallet.WealthType;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface WealthTypeDao extends CrudDao<WealthType> {

    /**
     * 设置其他类型非默认
     * @return
     */
    boolean updateNoDefault();

}
