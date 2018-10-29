/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql;

import java.util.Map;

/**
 * SQL 接口。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface ISql extends ISimpleSql
{
	/**
	 * @return SQL 参数值。
	 */
	Map<String, Object> values();
}