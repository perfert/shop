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

import com.mas.common.orm.util.Page;
import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.wallet.Withdraw;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface WithdrawQueryDao extends QueryDao<Withdraw>{

    void queryPage(String memberId, Page page);

    Long existCheck();

    void queryPageByAddress(Page page);

    void queryPage(String memberId, String wealthTypeId, Page page);

}
