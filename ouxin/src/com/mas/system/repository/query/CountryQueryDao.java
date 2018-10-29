package com.mas.system.repository.query;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.system.domain.entity.Country;

/**
 * 城市。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
public interface CountryQueryDao extends QueryDao<Country>
{
	/**
	 * 检索所有数据。
	 */
	List<Country> queryAll();

    boolean exist(String code);
}