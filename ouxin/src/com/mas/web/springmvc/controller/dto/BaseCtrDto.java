package com.mas.web.springmvc.controller.dto;

import com.mas.common.orm.util.Page;
import com.mas.core.domain.entity.Entity;

/**
 * Base Controller DTO.</br>
 * 
 * CRUD 中的CU对象. (以 'bean' 为 form 表单的前缀)</br>
 * 分页对象, 以 'pager' 为 form 表单的前缀.</br>
 * 检索对象, 以 'search' 为表单的前缀.
 * 
 * @version 1.0
 * 
 * @author MAS BY 2013-08-07
 *
 * @param <L> CRUD 中的CU对象类.
 */
public abstract class BaseCtrDto<L extends Entity>
{	
	private L bean;
	private L query;
	private Page pager;
	
	/** 
	 * @return CRUD 中的CU对象.
	 */
	public L getBean() {
		return bean;
	}
	public L setBean(L bean) {
		return
				this.bean = bean;
	}
	
	/**
	 * @return 分页对象.
	 */
	public Page getPager() {
		return this.pager;
	}
	
	public L getQuery()
	{
		return query;
	}
	
	public void setQuery( L query )
	{
		this.query = query;
	}
	
	/**
	 * 设置分页的页码,页行.
	 * 
	 * @param pageNo 页码.
	 * @param pageSize 页行.
	 * 
	 * @return this
	 */
	public Page getPager(int pageNo, int pageSize)
	{
		this.pager = new Page( pageNo, pageSize );
		return this.pager;
	}
	
	/**
	 * 设置分页的页码,页行.
	 * 
	 * @param pageNo 页码.
	 * 
	 * @return this
	 */
	public Page getPager(int pageNo)
	{
		this.pager = new Page( pageNo, null == this.pager ? Page.DEFAULT_PAGE_SIZE : this.pager.getPageSize() );
		return this.pager;
	}
	
	public void setPager(Page pager) {
		this.pager = pager;
	}
}