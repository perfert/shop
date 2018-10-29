/**    
 * 文件名：WealthService.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.wallet.Transfer;
import com.mas.user.repository.dao.wallet.TransferDao;
import com.mas.user.repository.query.wallet.TransferQueryDao;
/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class TransferService extends BaseServiceImpl<Transfer> {
    
    /**
     * 获取转账实体(包括收款人信息)
     * @param transferId
     * @return
     */
    public Transfer getByReceiveInfo(Object transferId) {
        if(null == transferId)
            return null;
        return queryDao.getByReceiveInfo(transferId);
    }
   
   
    @Autowired private TransferQueryDao queryDao;
    @Autowired private TransferDao transferDao;
    
    @Override
    protected CrudDao<Transfer> crudDao() {
        return transferDao;
    }
    
    @Override
    protected QueryDao<Transfer> queryDao() {
        // TODO Auto-generated method stub
        return queryDao;
    }

    

   

}
