/**    
 * 文件名：SignDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat;


import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.chat.Sign;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface SignDao extends CrudDao<Sign>{

    boolean delete(String signId, String userName);

    boolean deleteSignMemberBySignId(String signId);

    /**
     * 根据ID，获取数据对象。
     * 
     * @param signId 标签ID
     * 
     * @param detail 名称集合
     * 
     * @param count 总人数
     * 
     */
    boolean updateSign(Object signId, String detail, int count);


}
