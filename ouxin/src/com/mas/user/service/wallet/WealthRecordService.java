/**    
 * 文件名：WealthRecordService.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.wallet;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.repository.dao.wallet.WealthRecordDao;
import com.mas.user.repository.query.wallet.WealthRecordQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class WealthRecordService extends BaseServiceImpl<WealthRecord> {
    
    public void queryPage(String mid, Integer dataType, Page page) {
        queryDao.queryPage(mid, dataType,page);
    }
    
    public WealthRecord getMemberInfo(String detailId) {
        return get(detailId);
       /* if(StringUtils.isEmpty(detailId))
            return null;
        return queryDao.getMemberInfo(detailId);*/
    }
    
    /**
     * 更新提币记录状态
     * @param status
     * @param withdrawId
     * @param date
     * @return
     */
    public boolean updateStatusByWithdrawId(int status, String withdrawId, Date date) {
        if(StringUtils.isEmpty(withdrawId))
            return false;
        return dao.updateStatusByWithdrawId(status,withdrawId,date);
    }
    
    @Override
    protected CrudDao<WealthRecord> crudDao() {
        // TODO Auto-generated method stub
        return dao;
    }

    @Override
    protected QueryDao<WealthRecord> queryDao() {
        // TODO Auto-generated method stub
        return queryDao;
    }
    
    @Autowired private WealthRecordDao dao;
    @Autowired private WealthRecordQueryDao queryDao;
    
    
   
    
    
}
