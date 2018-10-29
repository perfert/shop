/**    
 * 文件名：ApkService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.file.util.FileUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.repository.dao.app.ApkDao;
import com.mas.user.repository.query.app.ApkQueryDao;

/**    
 * 项目名称：chat    
 * 类名称：    ApkService
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ApkService extends BaseServiceImpl<Apk> {

    public Apk getTheLatestApk() {
        return queryDao.getTheLatestApk();
    }
    
    @Override
    public boolean delete( Object id )
    {
        Apk apk = get(id);
        if(apk.getVersion() >= queryDao.getMaxVersion())
            throw new ServiceException("最新版本不允许删除!");
        boolean success = super.delete(id);
        if(success)
            FileUtil.remove(apk.getFilePath());
        return success;
    }
    
    @Override
    public Object persistence( Apk bean )
    {
        verify(bean);
        return super.persistence(bean);
    }
    
    @Override
    protected CrudDao<Apk> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Apk> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private ApkDao dao;
    @Autowired private ApkQueryDao queryDao;
    
    private void verify(Apk bean){
        if(!queryDao.isUnique(bean.getVersion()))
            throw new ServiceException("版本号不唯一");
        if(queryDao.getMaxVersion() >= bean.getVersion())
            throw new ServiceException("版本号不能低于当前最新版本");
    }

    
    
}
