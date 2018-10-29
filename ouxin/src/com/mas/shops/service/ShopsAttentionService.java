/**    
 * 文件名：ShopsAttentionService.java    
 *    
 * 版本信息：    
 * 日期：2018-5-12    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.entity.ShopsAttention;
import com.mas.shops.repository.dao.ShopsAttentionDao;
import com.mas.shops.repository.query.ShopsAttentionQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-5-12  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ShopsAttentionService extends BaseServiceImpl<ShopsAttention>{

    @Override
    protected CrudDao<ShopsAttention> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<ShopsAttention> queryDao() {
        return queryDao;
    }
    
    @Autowired
    private ShopsAttentionDao dao;
    @Autowired
    private ShopsAttentionQueryDao queryDao;
    
   

    public Object save(String memberId, String shopsId, String title, String logo, String describe, String link,int look) {
        ShopsAttention shopsAttention = new ShopsAttention();
        shopsAttention.setMemberId(memberId);
        shopsAttention.setShopsId(shopsId);
        shopsAttention.setTitle(title);
        shopsAttention.setLogo(logo);
        shopsAttention.setDetail(describe);
        shopsAttention.setLink(link);
        shopsAttention.setState(1);
        shopsAttention.setAttention(look);
        return dao.persistence(shopsAttention);
    }

    /**
     * 获取所有关注的店铺
     * @param mid
     * @return
     */
    public List<ShopsAttention> queryAttention(String mid) {
        if(StringUtils.isEmpty(mid) )
            throw new ServiceException("parameter.error");
        return queryDao.queryAttention(mid);
    }
    
    /**
     * 是否存在中间表
     * @param memberId
     * @param shopsId
     * @return
     */
    public boolean exist(String memberId, String shopsId) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(shopsId) )
            throw new ServiceException("parameter.error");
        return queryDao.exist(memberId,shopsId);
    }

    public boolean updateAttention(String memberId, String shopsId, int look) {
        if(StringUtils.isEmpty(memberId) || StringUtils.isEmpty(shopsId) )
            throw new ServiceException("parameter.error");
        return queryDao.updateAttention(memberId,shopsId,look);
    }
}
