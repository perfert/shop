/**    
 * 文件名：ApkQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.app;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.app.Apk;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    ApkQueryDao
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface ApkQueryDao extends QueryDao<Apk>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<Apk> queryAll( Object uId );

    /**
     * 版本号是否唯一
     * @param version
     * @return
     */
    public boolean isUnique(double version);
    
    /**
     * 获取最新的Apk
     * @return
     */
    public Apk getTheLatestApk();
    
    /**
     * 获取当前最新版本号
     * @return
     */
    public double getMaxVersion();

}