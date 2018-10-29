package com.mas.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.system.domain.entity.Country;
import com.mas.system.repository.dao.CountryDao;
import com.mas.system.repository.query.CountryQueryDao;

/**
 * CountryService
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class CountryService extends BaseServiceImpl<Country>
{
	public List<Country> queryAll()
	{
		return queryDao.queryAll();
	}
	
	/**
	 * 根据code判断国际码是否存在
	 * @param   code    国际码
	 */
	public boolean exist(String code) {
        return queryDao.exist(code);
    }

	@Override
	protected CrudDao<Country> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<Country> queryDao()
	{
		return queryDao;
	}
	
	@Autowired private CountryDao dao;
	@Autowired private CountryQueryDao queryDao;
    
    
}
