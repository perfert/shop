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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.vo.MortagagePayVo;
import com.mas.shops.domain.vo.WealthTypeVo;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.dao.wallet.WealthTypeDao;
import com.mas.user.repository.query.wallet.WealthTypeQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00 2018-2-27
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class WealthTypeService extends BaseServiceImpl<WealthType> {

    public List<WealthType> getAll() {
        return queryDao.getAll();
    }
    
    /**
     * 获取所有交易方式和会员的财富值
     * @return
     */
    public List<WealthType> getAllContainWealth(String mid) {
        if(StringUtils.isEmpty(mid))
            return null;
        else
            return queryDao.getAllContainWealth(mid);
    }
    
    /**
     * 
     * @param memberId
     * @param wealthTypeId
     * @return
     */
    public WealthType getWealth(String memberId, String wealthTypeId) {
        if(StringUtils.isEmpty(memberId)||StringUtils.isEmpty(wealthTypeId))
            return null;
        else
            return queryDao.getWealth(memberId,wealthTypeId);
    }
    
    public boolean isEnable(String wealthType) {
        return queryDao.isEnable(wealthType);
    }
    
    
    @Override
    public Object persistence( WealthType bean )
    {
        if(bean.getIsDefault()){
            dao.updateNoDefault();
        }
        return super.persistence(bean);
    }
    
    
    protected CrudDao<WealthType> crudDao() {
        return dao;
    }
    
    @Override
    protected QueryDao<WealthType> queryDao() {
        return queryDao;
    }
   
    @Autowired private WealthTypeDao dao;
    @Autowired private WealthTypeQueryDao queryDao;
   
    
    
    
    
}
