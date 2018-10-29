/**    
 * 文件名：SmsConfigService.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.repository.dao.SmsCodeDao;
import com.mas.system.repository.query.SmsCodeQueryDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SmsCodeService extends BaseServiceImpl<SmsCode>{

    
    public boolean checkCode(int type, String ituTCode, String mobile, String code) {
        if(StringUtils.isEmpty(ituTCode) || StringUtils.isEmpty(mobile) ||  StringUtils.isEmpty(code))
            return false;
        return queryDao.exist(type, ituTCode, mobile, code);
    }
    
    @Override
    protected CrudDao<SmsCode> crudDao()
    {
        return dao;
    }

   
    @Override
    protected QueryDao<SmsCode> queryDao()
    {
        return queryDao;
    }
    
    private static final Logger log = LogManager.getLogger();
    @Autowired private SmsCodeDao dao;
    @Autowired private SmsCodeQueryDao queryDao;
   
}
