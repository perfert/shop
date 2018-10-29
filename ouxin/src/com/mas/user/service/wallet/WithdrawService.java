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

import com.mas.common.orm.util.Page;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.wallet.Withdraw;
import com.mas.user.repository.dao.wallet.WithdrawDao;
import com.mas.user.repository.query.wallet.WithdrawQueryDao;
/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class WithdrawService extends BaseServiceImpl<Withdraw> {
    
    /**
     * 更新提币状态
     * @param status
     * @return
     */
    public boolean updateStatus(int status,String id) {
        return wthdrawDao.updateStatus(status,id);
    }
   
    /**
     * 获取提币列表
     * @param mid
     * @param page
     */
    @Deprecated
    public void queryPage(String memberId, Page page) {
        queryDao.queryPage(memberId,page);
    }
   
    /**
     * 获取提币列表
     * @param memberId
     * @param wealthTypeId
     * @param page
     */
    public void queryPage(String memberId, String wealthTypeId, Page page) {
        queryDao.queryPage(memberId,wealthTypeId,page);
    }
    
    /**
     * 提币成功回调
     * @param id
     */
    public void handleSuccess(String id) {
        wealthService.handleSuccess(id);
    }

    /**
     * 提币失败回调
     * @param id
     */
    public void handleFailed(String id) {
        wealthService.handleFailed(id);
    }
    
    /**
     * 有多少条未处理提币数据
     * @return
     */
    public Long existCheck() {
        return queryDao.existCheck();
    }
    
    /**
     * 查询状态为提币申请的结果页
     * @param page
     */
    public void queryPageByAddress(Page page) {
        queryDao.queryPageByAddress(page);
    }

    
    @Autowired private WithdrawQueryDao queryDao;
    @Autowired private WithdrawDao wthdrawDao;
    @Autowired private WealthService wealthService;
    
    @Override
    protected CrudDao<Withdraw> crudDao() {
        return wthdrawDao;
    }
    
    @Override
    protected QueryDao<Withdraw> queryDao() {
        return queryDao;
    }

   

   
}
