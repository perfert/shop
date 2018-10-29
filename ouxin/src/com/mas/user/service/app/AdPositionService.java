/**    
 * 文件名：AdPositionService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.app;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.domain.entity.app.AdPosition;
import com.mas.user.repository.dao.app.AdPositionDao;
import com.mas.user.repository.query.app.AdPositionQueryDao;

/**    
 * 项目名称：chat    
 * 类名称：  广告位置Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class AdPositionService extends BaseServiceImpl<AdPosition> {

    @Autowired private AdService adService;
    
    @Override
    public Object persistence( AdPosition bean )
    {
        verify(bean);
        return super.persistence(bean);
    }
    
    @Override
    public boolean delete( Object id )
    {
        //数量过多问题
        List<Ad> list = adService.queryAdListByPositionId(id);
        for(Ad ad : list){
            adService.delete(ad.getId());
        }
        return super.delete(id);
    }
    
    
    @Override
    protected CrudDao<AdPosition> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<AdPosition> queryDao()
    {
        return queryDao;
    }
    
    /**
     * 获取所有广告位置
     */
    public List<AdPosition> getAll(){
        return queryDao.queryAll();
    }
    
    @Autowired private AdPositionDao dao;
    @Autowired private AdPositionQueryDao queryDao;
    
    private void verify(AdPosition bean){
        if(StringUtils.isEmpty(bean.getName()))
            throw new ServiceException("名称不能为空");
        if(null == bean.getWidth())
            throw new ServiceException("宽度必填");
        if(null == bean.getHeight())
            throw new ServiceException("高度必填");
        if(StringUtils.isEmpty(bean.getSign()))
            throw new ServiceException("标记不能为空");
        Object id = bean.getId();
        String sign = bean.getSign();
        if(null == bean.getMaxAd() || bean.getMaxAd() < 0){
            throw new ServiceException("轮播图数量设置有误");
        }
        if(null != id){
            String old = dao.get(id).getSign();
            if(!sign.equalsIgnoreCase(old)){
                if(queryDao.exit(sign))
                    throw new ServiceException("标识已存在");
            }
        }else{
            if(queryDao.exit(sign))
                throw new ServiceException("标识已存在");
        }
    }

    /**
     * 
     * 根据广告号标识获取广告列表   
     * @param   sign    广告位标识
     * @param  
     */
    public List<Ad> getAdListBySign(String sign) {
        return queryDao.getAdListBySign(sign);
    }
    
}
