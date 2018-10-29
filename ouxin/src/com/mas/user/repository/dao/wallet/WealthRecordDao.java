/**    
 * 文件名：WealthRecordDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.wallet;

import java.util.Date;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.wallet.WealthRecord;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface WealthRecordDao extends CrudDao<WealthRecord> {

    boolean updateStatusByWithdrawId(int status, String withdrawId, Date date);
}
