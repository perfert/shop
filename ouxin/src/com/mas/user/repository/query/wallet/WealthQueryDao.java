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

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.wallet.Wealth;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface WealthQueryDao extends QueryDao<Wealth>{

    boolean exixt(String mid);

    Member getMemberByAddress(String address,boolean containMemberId);

    Wealth getByMemberId(String memberId, boolean inCludePay);

    Wealth getByMemberId(String memberId, String wealthTypeId, boolean containPayPwd);

    Wealth getByAddress(String address);
}
