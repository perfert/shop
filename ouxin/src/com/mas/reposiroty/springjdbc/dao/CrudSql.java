/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.reposiroty.springjdbc.dao;

import java.util.Date;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mas.common.orm.sql.ISql;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.common.orm.sql.util.Symbol;
import com.mas.common.verify.VerifyUtil;
import com.mas.core.domain.entity.Entity;
import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.Member;

/**
 * Entity CRUD DAO SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class CrudSql<E extends Entity> extends BaseSpringJdbcDao implements CrudDao<E>
{	
	@SuppressWarnings( "unchecked" )
	@Override
	public E get( Object id )
	{
		BeanPropertyRowMapper<Member> m = new BeanPropertyRowMapper<Member>();
	        return
				( E ) DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                            "SELECT * FROM " + tableName() + " WHERE ID = ?"
                            , new Object[] { id }
                            , new BeanPropertyRowMapper(super.ormClass)
                            ));
		
	}
	
	@Override
	public Object persistence( E bean )
	{
		if( VerifyUtil.isNotNull( bean ) )
		{
			Date currentDate = new Date();
			ISql sql = null;
			if( VerifyUtil.isNotNull( bean.getId() ) && VerifyUtil.isNotBlank( bean.getId().toString() ) )
			{
				sql = updateSql( bean )
						.addField( "PRIORITY", null == bean.getPriority() ? 100 : bean.getPriority() )
						.addField( "STATE", null == bean.getState() ? 0 : bean.getState() )
						.addField( "CREATE_DATE", bean.getCreateDate() )
						.addField( "MODIFY_DATE", currentDate )
						.andWhere( "STATE", Symbol.ne, -1 );		// 已删除状态不能更新
				String s = sql.sql();
				int result = getNamedParameterJdbcTemplate().update(
						sql.sql()
						, sql.values()
						);
				return 0 < result ? bean.getId()
						: null;
			} else {
				sql = insertSql( bean )
						.addField( "PRIORITY", null == bean.getPriority() ? 100 : bean.getPriority() )
						.addField( "STATE", null == bean.getState() ? 0 : bean.getState() )
						.addField( "CREATE_DATE", currentDate )
						.addField( "MODIFY_DATE", currentDate );
				KeyHolder keyHolder = new GeneratedKeyHolder();
		        getNamedParameterJdbcTemplate().update(sql.sql(), new MapSqlParameterSource(sql.values()), keyHolder);
		        return keyHolder.getKey().longValue();
			}
		}
		return null;
	}
	
	@Override
	public boolean modifyState(Object id, StateVo state)
	{
		return 0 <
				getJdbcTemplate().update(
					"UPDATE " + tableName() + " SET state = ? WHERE state <> -1 AND ID = ?"
					, new Object[] { state.value(), id }
					);
	}
	
	@Override
	public boolean delete( Object id )
	{
		return 0 <
				getJdbcTemplate().update(
					"DELETE FROM " + tableName() + " WHERE ID = ?"
					, new Object[] { id }
					);
	}
	
	/**
	 * Insert SQL。
	 * 
	 * @param bean 数据对象。
	 * 
	 * @return {@link InsertSql }
	 */
	protected abstract InsertSql insertSql( E bean );
	
	/**
	 * Update SQL。
	 * 
	 * @param bean 数据对象。
	 * 
	 * @return {@link UpdateSql }
	 */
	protected abstract UpdateSql updateSql( E bean );
	
	/**
	 * @return 数据表名。
	 */
	protected abstract String tableName();
}