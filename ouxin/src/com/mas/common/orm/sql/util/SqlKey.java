/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.util;

/**
 * SQL 关键词。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public enum SqlKey 
{
	// 检索
	SELECT, FROM, WHERE
	
	// 排序
	, ORDERBY("ORDER BY")
	
	// 联合查询
	, JOIN("INNER JOIN"), LJOIN("LEFT JOIN"), RJOIN("RIGHT JOIN"), CJOIN("CROSS JOIN"), UNION("UNION")
	
	// 分组查询
	, GROUPBY("GROUP BY"), HAVING
	
	// DML 关键字
	, INSERT("INSERT INTO"), DELETE("DELETE FROM"), UPDATE, SET, VALUES
	
	// TPL 关键字
	, BEGING, TRANSCATION, COMMIT, ROLLBACK
	
	// DDL 关键字
	, DROPTABLE("DROP TABLE"), DROPUSER("DROP USER"), DROPTTABLESPACE("DROP TABLESPACE")
	, TRUNCATE("TRUNCATE TABLE")			// 连同索引一并删除,数据无法通过闪回找回,相当于永久删除, (慎用)
	
	// 排序关键字
	, DESC, ASC
	
	// 别名
	, AS
	
	// 函数名
	, COUNT
	;
	
	public String value() { return this.value; }
	@Override public String toString() { return value(); }
	
	private String value;
	private SqlKey() { this.value = this.name(); };
	private SqlKey(String value) { this.value = value; };
}