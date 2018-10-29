/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.reposiroty.springjdbc.query;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.mas.common.orm.sql.ISql;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.sql.util.SqlKey;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.core.domain.entity.Entity;
import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.query.QueryDao;
import com.mas.reposiroty.springjdbc.dao.BaseSpringJdbcDao;

/**
 * Entity Query DAO SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class QuerySql<E extends Entity> extends BaseSpringJdbcDao implements QueryDao<E>
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
	public void queryPage( Page page, StateVo state )
	{
		if( page.isAutoCount() )
		{
			page.setTotalCount(
					DataAccessUtils.uniqueResult(
							null == state ?
								getJdbcTemplate().query(
										"SELECT COUNT(*) FROM " + tableName()
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								: getJdbcTemplate().query(
										"SELECT COUNT(*) FROM " + tableName() + " WHERE STATE = ?"
										, new Object[] { state.value() }
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								)
					);
		}
		String pageSql = pageSql( page );
		page.setResult(
				null == state ?
					getJdbcTemplate().query(
							"SELECT * FROM " + tableName()  + " ORDER BY MODIFY_DATE DESC" + pageSql
							, new BeanPropertyRowMapper(super.ormClass)
							)
					: getJdbcTemplate().query(
							"SELECT * FROM " + tableName() + " WHERE STATE = ?"  + " ORDER BY MODIFY_DATE DESC" + pageSql
							, new Object[] { state.value() }
							, new BeanPropertyRowMapper(super.ormClass)
							)
					);
	}
	
	@Override
	public void queryPage( Page page, E query )
	{
		SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() ).addDesc( "ID" )
					: querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );
		String queryString = sql.sql();
		String fromSql = getFromToOrderBySql( queryString );
		
		if( page.isAutoCount() )
		{
			page.setTotalCount(
					DataAccessUtils.uniqueResult(
							VerifyUtil.isEmpty( sql.values() ) ?
								getJdbcTemplate().query(
										"SELECT COUNT(*) " + fromSql
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								: getNamedParameterJdbcTemplate().query(
										"SELECT COUNT(*) " + fromSql
										, sql.values()
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								)
					);
		}
		String pageSql = pageSql( page );
		page.setResult(
				VerifyUtil.isEmpty( sql.values() ) ?
					getJdbcTemplate().query(
							queryString + pageSql
							, new BeanPropertyRowMapper(super.ormClass)
							)
					: getNamedParameterJdbcTemplate().query(
							queryString + pageSql
							, sql.values()
							, new BeanPropertyRowMapper(super.ormClass)
							)
					);
	}
	
	/**
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 * 
	 * @return 检索所有数据对象。
	 */
	@SuppressWarnings( "unchecked" )
	protected List<E> queryAll( StateVo state )
	{
	    
		List<?> result =
				null == state ?
					getJdbcTemplate().query(
							"SELECT * FROM " + tableName()
							, new BeanPropertyRowMapper(super.ormClass)
							)
					: getJdbcTemplate().query(
							"SELECT * FROM " + tableName() + " WHERE STATE =?"
							, new Object[] { state.value() }
							, new BeanPropertyRowMapper(super.ormClass)
							);
		return ( List<E> ) result;
	}
	
	/**
	 * 分页SQL.
	 * 
	 * @param page 分页对象。
	 * 
	 * @return String or ""
	 */
	protected String pageSql(Page page)
	{
		if( null != page )
		{
			return 
					" LIMIT " 
					+ (Math.max( page.getPageNo(), Page.DEFAULT_PAGE_NO) - 1 ) * Math.min( page.getPageSize(), Page.MAX_PAGE_SIZE )
					+ ", " 
					+ Math.min( page.getPageSize(), Page.MAX_PAGE_SIZE );
		} else {
			return "";
		}
	}
	
	/**
	 * Query SQL。
	 * 
	 * @param query 数据对象。
	 * 
	 * @return {@link ISql }
	 */
	protected abstract SimpleQuerySql querySql( E query );
	
	/**
	 * @return 数据表名。
	 */
	protected abstract String tableName();
	
	/**
	 * get start from end order by SQL.
	 * 
	 * @param queryString
	 * 
	 * @return queryString
	 */
	protected String getFromToOrderBySql(String queryString)
	{
		String fromHql = SqlKey.FROM.value() + StringUtils.substringAfter( queryString, SqlKey.FROM.value() );
		fromHql = StringUtils.substringBefore( fromHql, SqlKey.ORDERBY.value() );
		
		return fromHql;
	}
	
	protected String pageSql(int pageNo, int pageSize) {
        return 
                " LIMIT " 
                + (Math.max( pageNo, Page.DEFAULT_PAGE_NO) - 1 ) * Math.min( pageSize, Page.MAX_PAGE_SIZE )
                + ", " 
                + Math.min( pageSize, Page.MAX_PAGE_SIZE );
        
    }
    
}