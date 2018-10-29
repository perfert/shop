/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.dao;

import java.util.Date;

import com.mas.user.domain.entity.Member;

/**
 * 会员
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface MemberDao
{
	/**
	 * 根据ID，获取数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return {@link Member} or null。
	 */
	Member get(Object id);
	
	/**
	 * 持久化数据对象。
	 * 
	 * @param bean 数据对象。
	 * 
	 * @return 持久化数据的数据ID。
	 */
	Object persistence(Member bean);

	/**
	 * 物理删除节点及其所有子节点。
	 * 
	 * @return true or false。
	 */
	boolean delete( Object id );

	/**
	 * 仅仅删除节点下面的所有子节点。（物理删除）
	 * 
	 * @return true or false。
	 */
	boolean deleteChildren( Object id ); 
	
	/**
	 * 根据账号或wxid，判断是否存在数据。
	 * 
	 * @param account 账号
	 * @param wxid wxid
	 * 
	 * @return true or false
	 */
	boolean existMember( String code, String account, String wxid );
	
	/**
	 * 根据账号或wxid，获取数据。
	 * 
	 * @param accountOrwxid 账号 or wxid
	 * 
	 * @return {@link Member} or null
	 */
	Member queryBy( String code, String accountOrwxid );
	
	boolean updatePassword(Object id, String newPwd);
	
	boolean updatePayPassword(Object id, String newPwd);
	
	boolean updateWxid(Object id, String uuid);
	
	boolean updatePushid(Object id, String pushId);
	
	boolean bind(Object id, String code, String mobile, String password );
	
	boolean change(Object id, Object delId);
	
	/**
	 * 更新会员属性
	 * @param userName
	 * @param field 字段
	 * @param value
	 * @param field2 字段2
	 * @param value2
	 * @return
	 */
	boolean updateField(String userName,String field, String value,String field2,String value2);

	/**
	 * 更新会员属性
	 * @param userId
	 * @param field
	 * @param value
	 * @return
	 */
    boolean updateField(String userId, String field, String value);

    boolean updatePayFailure(Object memberId, int count, Date date);

    boolean updateToken(String mid, String token, Date date);

}