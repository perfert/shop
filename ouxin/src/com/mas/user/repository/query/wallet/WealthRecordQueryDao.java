/**    
 * 文件名：WealthRecordQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.wallet;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.wallet.WealthRecord;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface WealthRecordQueryDao extends QueryDao<WealthRecord>{

    void queryPage(String mid, Integer dataType, Page page);

    WealthRecord getMemberInfo(String detailId);

}
