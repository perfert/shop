/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.query;

import java.util.Date;
import java.util.List;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.vo.MemberDetail;
import com.mas.user.domain.entity.vo.MemberVo;

/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface MemberQueryDao extends QueryDao<Member>
{
	Member queryById(Object id);
	
	Member queryByAccount(String account);
	
	void queryPage( Page page, MemberVo query );
	
	/**
	 * 检索 count 个 level 等级的父级。
	 * 
	 * @param id 数据ID。
	 * @param level 等级。
	 * @param count 数量（0为所有）。
	 * 
	 * @return ｛@link List} or null
	 */
	List<Member> queryAllParent(Object id, Integer level, Integer count);
	
	/**
	 * 获取子层。
	 * 
	 * @param id 会员ID。
	 * @param layer 第几层 (1-3)
	 * @param level 子层等级（可空）。
	 * 
	 * @return ｛@link List} or null
	 */
	List<Member> queryLayerChild(Object id, int layer, Integer level);
	
	/**
	 * 获取子层子账户。
	 * 
	 * @param id 会员ID。
	 * @param layer 第几层 (1-3)
	 * @param level 子层等级（可空）。
	 * 
	 * @return ｛@link List} or null
	 */
	List<Member> querySubLayerChild(Object id, int layer, Integer level);
	
	/**
	 * 是否本人子账户
	 * @param id
	 * @param subId
	 * @return
	 */
	boolean isMySub(Object id, Object subId);
	
	/**
	 * 分页获取子层。
	 * 
	 * @param id 会员ID。
	 * @param layer 第几层 (1-3)
	 * @param level 子层等级（可空）。
	 * 
	 * @return ｛@link List} or null
	 */
	void queryLayerChild(Page page, Object id, int layer, Integer level);
	
	/**
	 * 分页获取子层子账户。
	 * 
	 * @param id 会员ID。
	 * @param layer 第几层 (1-3)
	 * @param level 子层等级（可空）。
	 * 
	 * @return ｛@link List} or null
	 */
	void querySubLayerChild(Page page, Object id, int layer, Integer level);
	
	/**
	 * 介于 deductMemberId 与 orderMemberId 之间，是否存在与 deductMemberId 相同等级的会员。
	 * 
	 * @param deductMemberId 需要等级提成的会员ID
	 * @param orderMemberId 订单会员ID
	 * 
	 * @return true or false。
	 */
	boolean existSamLevel(Object deductMemberId, Object orderMemberId);

	/**
     * 根据手机或O信号获取会员
     * 
     * @param account 手机或O信号
     * 
     */
    Member getByPhoneOrWxid(String account);

    /**
     * 通过code_account查询用户
     * 
     * @param  username
     */
    Member queryMemberByUsername(String username);

    /**
     * 获取TOKEN
     * @param mid
     * @param date
     * @param day
     * @return
     */
    String getToken(String mid, Date date, int day);

    List<MemberDetail> getFriendDetail(String mid, String friendId,int num);

    List<MemberDetail> getMemberDetail(String mid,int num);

    Member getByUsername(String username);

    Member getByToken(String token);
    
}