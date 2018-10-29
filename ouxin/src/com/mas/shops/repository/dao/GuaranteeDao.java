/**    
 * 文件名：CertificateDao.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.repository.dao;

import com.mas.core.repository.dao.CrudDao;
import com.mas.shops.domain.entity.Guarantee;
/**    
 * 类名称：  保证金Dao  
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
public interface GuaranteeDao extends CrudDao<Guarantee> {

    boolean updateStatus(Object id, int state);

}
