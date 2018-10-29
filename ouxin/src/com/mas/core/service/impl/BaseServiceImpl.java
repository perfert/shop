/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.service.impl;

import com.mas.common.orm.util.Page;
import com.mas.core.cache.Cache;
import com.mas.core.domain.entity.Entity;
import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.BaseService;

/**
 * Crud service implement.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class BaseServiceImpl<E extends Entity>implements BaseService<E>
{
	@Override
	public E get( Object id )
	{
		return crudDao().get( id );
	}

	@Override
	public Object persistence( E bean )
	{
		Object object = crudDao().persistence( bean );
		if( null != cache() )
		{
			cache().notifyHasChange();
		}
		return object;
	}

	@Override
	public boolean enable( Object id )
	{
		boolean result = crudDao().modifyState( id, StateVo.ENABLE );
		if( result && null != cache() )
		{
			cache().notifyHasChange();
		}
		return result;
	}

	@Override
	public boolean disable( Object id )
	{
		boolean result = crudDao().modifyState( id, StateVo.DISABLE );
		if( result && null != cache() )
		{
			cache().notifyHasChange();
		}
		return result;
	}

	@Override
	public boolean remove( Object id )
	{
		boolean result = crudDao().modifyState( id, StateVo.REMOVE );
		if( result && null != cache() )
		{
			cache().notifyHasChange();
		}
		return result;
	}
	
	@Override
	public boolean delete( Object id )
	{
		boolean result = crudDao().delete( id );
		if( result && null != cache() )
		{
			cache().notifyHasChange();
		}
		return result;
	}

	@Override
	public void queryPage( Page page, StateVo state )
	{
		queryDao().queryPage( page, state );
	}

	@Override
	public void queryPage( Page page, E query )
	{
		queryDao().queryPage( page, query );
	}
	
	
	protected Cache cache() { return null; }
	
	protected abstract CrudDao<E> crudDao();
	protected abstract QueryDao<E> queryDao();
}