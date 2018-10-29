/**    
 * 文件名：SnDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-13    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.wallet;


/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-13  
 */
public interface SnDao {
    /**
     * 生成序列号
     * 
     * @param type
     *            类型
     * @return 序列号
     */
    String generate();

}