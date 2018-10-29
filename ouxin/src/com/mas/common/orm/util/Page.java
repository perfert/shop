/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.util;

import java.util.List;

/**
 * Page auxiliary utility.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class Page 
{
	public static final int DEFAULT_PAGE_NO = 1;
	public static final int DEFAULT_PAGE_SIZE = 15;
	public static final int MAX_PAGE_SIZE = 200;
	public static final long DEFAULT_DATA_TOTAL_COUNT = -1L;
	
	private int pageNo;
	private int pageSize;
	private long totalCount;
	private boolean autoCount;
	private List<?> result;
		
	/**
	 * @return 页码.
	 */
	public int getPageNo()
	{
		return this.pageNo;
	}
	
	/**
	 * @return 每页显示数量.
	 */
	public int getPageSize()
	{
		return this.pageSize;
	}
	
	/**
	 * @return 统计的数据总数量.
	 */
	public long getTotalCount()
	{
		return this.totalCount;
	}
	
	/** 
	 * @return 总页码数量
	 */
	public long getTotalPageNo()
	{
		final long getTotalcount = getTotalCount();
		if ( 0L > getTotalcount ) {
			return DEFAULT_DATA_TOTAL_COUNT;
		}
		long count = getTotalcount / getPageSize();
		return
				0L >= getTotalcount % getPageSize() ? count
						: count + 1;
	}
	
	/**
	 * @return 下一页页码
	 */
	public long getNextPageNo() {
		return
				hasNextPageNo() ? getPageNo() + 1
						: getPageNo();
	}
	
	/**
	 * @return 前一页页码
	 */
	public long getPrePageNo() {
		return
				hasPrePageNo() ? getPageNo() - 1
						: getPageNo();
	}
	
	/**
	 * @return 存在下一页页码 ( true / false )
	 */
	public boolean hasNextPageNo() {
		return
				getTotalPageNo() > getPageNo() ? true
						: false;
	}
	
	/**
	 * @return 存在前一页页码
	 */
	public boolean hasPrePageNo() {
		return
				1L < getPageNo() ? true
						: false;
	}
	
	/**
	 * @return 是否统计数据数量.
	 */
	public boolean isAutoCount()
	{
		return this.autoCount;
	}
	
	/**
	 * @return 结果集.
	 */
	public List<?> getResult()
	{
		return this.result;
	}
	
	/**
	 * 设置数据总数量.
	 * 
	 * @param totalCount 数据总数量.
	 * 
	 * @return this.
	 */
	public Page setTotalCount(Long totalCount)
	{
		this.totalCount = null != totalCount ? totalCount
				: DEFAULT_DATA_TOTAL_COUNT;
		return this;
	}
	
	/**
	 * 设置结果集.
	 * 
	 * @param result 结果集.
	 * 
	 * @return this.
	 */
	public Page setResult(List<?> result)
	{
		this.result = result;
		return this;
	}
	
	/**
	 * 设置页码.
	 */
	public Page setPageNo(int pageNo)
	{
		this.pageNo = Math.max(DEFAULT_PAGE_NO, pageNo);
		return this;
	}
	
	/**
	 * 设置每页显示数量.
	 */
	public Page setPageSize(int pageSize)
	{
		this.pageSize = 0 >= pageSize ? DEFAULT_PAGE_SIZE
				: Math.min(MAX_PAGE_SIZE, pageSize);
		return this;
	}
	
	/**
	 * 设置是否统计数据数量.
	 */
	private Page isAutoCount(boolean autoCount)
	{
		this.autoCount = autoCount;
		return this;
	}
		
	/**
	 * 分页.
	 */
	public Page()
	{
		this(DEFAULT_PAGE_SIZE);
	}

	/**
	 * 分页.
	 * 
	 * @param pageSize 每页显示数量.
	 */
	public Page(int pageSize)
	{
		this(DEFAULT_PAGE_NO, pageSize);
	}

	
	/**
	 * 分页.
	 * 
	 * @param pageNo 页码.
	 * @param pageSize 每页显示数量.
	 */
	public Page(int pageNo, int pageSize)
	{
		this(pageNo, pageSize, Boolean.TRUE);
	}

	/**
	 * 分页.
	 * 
	 * @param pageNo 页码.
	 * @param pageSize 每页显示数量.
	 * @param autoCount 是否自动统计数据总数量.
	 */
	public Page(int pageNo, int pageSize, boolean autoCount)
	{
		super();
				
		setPageNo(pageNo);
		setPageSize(pageSize);
		isAutoCount(autoCount);		
	}
}