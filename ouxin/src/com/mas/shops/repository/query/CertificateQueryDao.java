/**    
 * 文件名：CertificateQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.repository.query;

import com.mas.core.repository.query.QueryDao;
import com.mas.shops.domain.entity.Certificate;

/**    
 * 类名称：证书    QueryDao
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
public interface CertificateQueryDao extends QueryDao<Certificate>{

    Certificate getByShopsId(String id);
    
    
}