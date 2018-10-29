/**    
 * 文件名：MsgRecordService.java    
 *    
 * 版本信息：    
 * 日期：2016-01-14    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.chat.MsgCollectionContent;
import com.mas.user.repository.dao.chat.MsgCollectionContentDao;
import com.mas.user.repository.query.chat.MsgCollectionContentQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    收藏Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class MsgCollectionContentService extends BaseServiceImpl<MsgCollectionContent> {

    public List<MsgCollectionContent> getAll(Object collectionId){
        if(null != collectionId)
            return queryDao.queryAll(collectionId);
        return null;
    }
    
    
    @Override
    protected CrudDao<MsgCollectionContent> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<MsgCollectionContent> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private MsgCollectionContentDao dao;
    @Autowired private MsgCollectionContentQueryDao queryDao;

   
    
   

}
