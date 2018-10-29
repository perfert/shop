/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.query.sql;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.common.util.DateTimeUtil;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.domain.entity.news.Article;
import com.mas.user.domain.entity.vo.MemberDetail;
import com.mas.user.domain.entity.vo.MemberVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.repository.query.MemberQueryDao;

/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class MemberQuerySql extends QuerySql<Member> implements MemberQueryDao{
    
    //查看朋友的信息,朋友的朋友圈,我设置的好友备注
    @Override
    public List<MemberDetail> getFriendDetail(String mid, String friendId, int num) {
        String sql = " SELECT bean.*,a.TYPE AS TYPE,a.PREFIX AS PREFIX,a.IMG_PATH AS IMG_PATH,a.THUMB_PATH AS THUMB_PATH," +
        		" f.ALIAS AS ALIAS,f.PHONE AS PHONE,f.DETAIL AS DETAIL,f.IMG AS IMG FROM " + tableName() + " bean " +
                " LEFT OUTER JOIN " + Friends.TABLE_NAME + " f ON bean.ID = f.FRIEND_ID " +
        		" LEFT OUTER JOIN " + Article.TABLE_NAME + " a ON bean.ID = a.MEMBER_ID " +
                " WHERE bean.ID = ? AND f.MEMBER_ID = ? AND f.FRIEND_ID = ? " + 
                " ORDER BY a.MODIFY_DATE DESC " + pageSql(0, num);
        return getJdbcTemplate().query(
                sql
                ,  new Object[] { friendId, mid , friendId } 
                , new BeanPropertyRowMapper<MemberDetail>(MemberDetail.class)
                );
    }
    
    
    @Override
    public List<MemberDetail> getMemberDetail(String mid,int num) {
        String sql = " SELECT bean.*,a.TYPE AS TYPE,a.PREFIX AS PREFIX,a.IMG_PATH AS IMG_PATH,a.THUMB_PATH AS THUMB_PATH  FROM " + tableName() + " bean LEFT OUTER JOIN " + Article.TABLE_NAME + " a ON bean.ID = a.MEMBER_ID " +
        		" WHERE bean.ID = ?  " + 
        		" ORDER BY a.MODIFY_DATE DESC " + pageSql(0, num);
        return getJdbcTemplate().query(
                sql
                ,  new Object[] { mid } 
                , new BeanPropertyRowMapper<MemberDetail>(MemberDetail.class)
                );
    }

    @Override
    public Member getByToken(String token) {
        String sql ="SELECT bean.* FROM " + tableName() + " bean WHERE bean.TOKEN = ? ";
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                sql
                ,  new Object[] { token } 
                , new BeanPropertyRowMapper<Member>(Member.class)
                ));
    }
    
    @Override
    public String getToken(String mid, Date date, int day) {
        /*String sql ="SELECT bean.TOKEN FROM " + tableName() + " bean WHERE bean.ID = ? ";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql
                        , new Object[] { mid } 
                        , new SingleColumnRowMapper<String>(String.class)
                        )
                );*/
        String sql ="SELECT bean.TOKEN FROM " + tableName() + " bean WHERE bean.ID = ? and bean.TOKEN_DATE > ? ";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql
                        , new Object[] { mid , DateTimeUtil.addDays(new Date(), -day)} 
                        , new SingleColumnRowMapper<String>(String.class)
                        )
                );
    }

    
    @Override
    public Member queryByAccount(String account) {
        String sql = "SELECT bean.* FROM " + tableName() + " bean WHERE bean.ACCOUNT = ?";
        return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { account }
                                , new BeanPropertyRowMapper<Member>( Member.class )
                                )
                        );
    }
    
    
    @Override
    public Member queryMemberByUsername(String username) {
        String sql = "SELECT bean.* FROM " + tableName() + " bean WHERE bean.USERNAME = ? OR bean.ACCOUNT = ?";
        return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { username , username }
                                , new BeanPropertyRowMapper<Member>( Member.class )
                                )
                        );
    }
    
    @Override
    public Member getByPhoneOrWxid(String account) {
        String sql = "SELECT bean.* FROM " + tableName() + " bean WHERE bean.MOBILE = ? OR bean.WXID = ? ";
        return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { account ,account}
                                , new BeanPropertyRowMapper<Member>( Member.class )
                                )
                        );
    }

    @Override
    public Member getByUsername(String username) {
        String sql = "SELECT bean.* FROM " + tableName() + " bean WHERE bean.USERNAME = ? ";
        return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { username}
                                , new BeanPropertyRowMapper<Member>( Member.class )
                                )
                        );
    }
    
	@Override
	public Member queryById( Object id )
	{
		String sql = "SELECT bean.*"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 1) AS ONE_JD_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 2) AS ONE_JK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 3) AS ONE_YK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 4) AS ONE_PT_COUNT"
				
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 1) AS ONE_SUB_JD_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 2) AS ONE_SUB_JK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 3) AS ONE_SUB_YK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 4) AS ONE_SUB_PT_COUNT"
				
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE SECOND_PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 2) AS TWO_JK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE SECOND_PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 3) AS TWO_YK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE SECOND_PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 4) AS TWO_PT_COUNT"
				
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE SECOND_PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 2) AS TWO_SUB_JK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE SECOND_PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 3) AS TWO_SUB_YK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE SECOND_PARENT_ID = bean.ID AND (IS_SUB = 1 AND bean.MOBILE = MOBILE AND bean.CODE = CODE) AND LEVEL = 4) AS TWO_SUB_PT_COUNT"
				
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE THIRD_PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 2) AS THREE_JK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE THIRD_PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 3) AS THREE_YK_COUNT"
				+ ", (SELECT COUNT(*) FROM " + tableName() + " WHERE THIRD_PARENT_ID = bean.ID AND (IS_SUB = 0 OR (bean.MOBILE = MOBILE AND bean.CODE = CODE)) AND LEVEL = 4) AS THREE_PT_COUNT"
				
				+ " FROM " + tableName() + " bean WHERE bean.ID = ?";
		return
				DataAccessUtils.uniqueResult(
						getJdbcTemplate().query(
								sql
								, new Object[] { id }
								, new BeanPropertyRowMapper<Member>( Member.class )
								)
						);
	}
	
	
	@Override
	public boolean existSamLevel( Object deductMemberId, Object orderMemberId )
	{
		return
				0 < DataAccessUtils.uniqueResult(
						getJdbcTemplate().query(
								"SELECT COUNT(bean.ID) FROM " + tableName() + " bean LEFT JOIN " + tableName() + " p ON bean.LFT > p.LFT AND bean.RGT < p.RGT AND bean.LEVEL = p.LEVEL LEFT JOIN " + tableName() + " my ON bean.LFT < my.LFT AND bean.RGT > my.RGT WHERE p.ID = ? AND my.ID = ?"
								, new Object[] { deductMemberId, orderMemberId }
								, new SingleColumnRowMapper<Long>(Long.class)
								)
						);
	}
	
	@Override
	public boolean isMySub( Object id, Object subId )
	{
		return
				0 < DataAccessUtils.uniqueResult(
						getJdbcTemplate().query(
								"SELECT COUNT(bean.ID) FROM " + tableName() + " bean LEFT JOIN " + tableName() + " p ON p.MOBILE = bean.MOBILE AND p.CODE = bean.CODE WHERE p.ID = ? AND bean.ID = ? AND bean.IS_SUB = 1 AND ( bean.PARENT_ID = p.ID OR bean.SECOND_PARENT_ID = p.ID OR bean.THIRD_PARENT_ID = p.ID) "
								, new Object[] { id, subId }
								, new SingleColumnRowMapper<Long>(Long.class)
								)
						);
	}
	
	@Override
	public List<Member> queryLayerChild( Object id, int layer, Integer level )
	{
		if( 1 > layer || 3 < layer ) return null;
		String sql = "SELECT * FROM " + tableName() + " WHERE " + (1 == layer ? "PARENT_ID" : (2 == layer ? "SECOND_PARENT_ID" : "THIRD_PARENT_ID")) + " = ? " + ( null != level && 0 < level ? " AND LEVEL = ?" : "" );
		return
				getJdbcTemplate().query(
						sql
						, ( null != level && 0 < level ? new Object[] { id, level }
								: new Object[] { id } )
						, new BeanPropertyRowMapper<Member>( Member.class )
						);
	}
	
	@Override
	public List<Member> querySubLayerChild( Object id, int layer, Integer level )
	{
		if( 0 > layer || 3 < layer ) return null;
		String sql = "";
		if( 0 == layer )
			sql = "SELECT bean.* FROM " + tableName() + " bean LEFT JOIN " + tableName() + " p ON p.MOBILE = bean.MOBILE AND p.CODE = bean.CODE WHERE p.ID = ? AND bean.IS_SUB = 1 AND ( bean.PARENT_ID = p.ID OR bean.SECOND_PARENT_ID = p.ID OR bean.THIRD_PARENT_ID = p.ID) " + ( null != level && 0 < level ? " AND bean.LEVEL = ?" : "" );
		else
			sql = "SELECT bean.* FROM " + tableName() + " bean LEFT JOIN " + tableName() + " p ON p.MOBILE = bean.MOBILE AND p.CODE = bean.CODE WHERE p.ID = ? AND bean.IS_SUB = 1 " + (1 == layer ? "AND bean.PARENT_ID" : (2 == layer ? "AND bean.SECOND_PARENT_ID" : " AND bean.THIRD_PARENT_ID")) + " = p.ID " + ( null != level && 0 < level ? " AND bean.LEVEL = ?" : "" );
		
		return
				getJdbcTemplate().query(
						sql
						, ( null != level && 0 < level ? new Object[] { id, level }
								: new Object[] { id } )
						, new BeanPropertyRowMapper<Member>( Member.class )
						);
	}
	
	@Override
	public void queryLayerChild( Page page, Object id, int layer, Integer level )
	{
		if( 1 > layer || 3 < layer ) return;
		String sql = " FROM " + tableName() + " WHERE " + (1 == layer ? "PARENT_ID" : (2 == layer ? "SECOND_PARENT_ID" : "THIRD_PARENT_ID")) + " = ? " + ( null != level && 0 < level ? " AND LEVEL = ?" : "" );
		
		if( page.isAutoCount() )
		{
			page.setTotalCount(
					DataAccessUtils.uniqueResult(
								getJdbcTemplate().query(
										"SELECT COUNT(*) " + sql
										, ( null != level && 0 < level ? new Object[] { id, level }
											: new Object[] { id } )
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								)
					);
		}
		String pageSql = pageSql( page );
		page.setResult(
					getJdbcTemplate().query(
							"SELECT * " + sql + " ORDER BY ID DESC " + pageSql
							, ( null != level && 0 < level ? new Object[] { id, level }
								: new Object[] { id } )
							, new BeanPropertyRowMapper<MemberVo>(MemberVo.class)
							)
					);
	}
	
	@Override
	public void querySubLayerChild( Page page, Object id, int layer, Integer level )
	{
		if( 1 > layer || 3 < layer ) return;
		
		String sql = " FROM " + tableName() + " bean LEFT JOIN " + tableName() + " p ON p.MOBILE = bean.MOBILE AND p.CODE = bean.CODE WHERE p.ID = ? AND bean.IS_SUB = 1 " + (1 == layer ? " AND bean.PARENT_ID" : (2 == layer ? " AND bean.SECOND_PARENT_ID" : " AND bean.THIRD_PARENT_ID")) + " = p.ID " + ( null != level && 0 < level ? " AND bean.LEVEL = ?" : "" );
		
		if( page.isAutoCount() )
		{
			page.setTotalCount(
					DataAccessUtils.uniqueResult(
								getJdbcTemplate().query(
										"SELECT COUNT(bean.*) " + sql
										, ( null != level && 0 < level ? new Object[] { id, level }
											: new Object[] { id } )
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								)
					);
		}
		String pageSql = pageSql( page );
		page.setResult(
					getJdbcTemplate().query(
							"SELECT bean.* " + sql + " ORDER BY ID DESC " + pageSql
							, ( null != level && 0 < level ? new Object[] { id, level }
								: new Object[] { id } )
							, new BeanPropertyRowMapper<MemberVo>(MemberVo.class)
							)
					);
	}
	
	@Override
	public List<Member> queryAllParent( Object id, Integer level, Integer count )
	{
		String sql = "SELECT bean.* FROM " + tableName() + " bean LEFT JOIN " + tableName() + " my ON bean.LFT < my.LFT AND bean.RGT > my.RGT WHERE my.ID = ? AND bean.LEVEL = ? ORDER BY bean.LFT DESC " + ( 0 < count ? "LIMIT 0, ?" : "" );
		return
				getJdbcTemplate().query(
						sql
						, ( 0 < count ? new Object[] { id, level, count }
								: new Object[] { id, level } )
						, new BeanPropertyRowMapper<Member>( Member.class )
						);
	}
	
	@Override
	public void queryPage( Page page, MemberVo query )
	{
		String selectSql = "SELECT bean.* ";
		String fromSql = "FROM " + tableName() + " bean  WHERE 1=1 ";
		Map<String, Object> values = new HashMap<String, Object>();
		if( null != query )
		{
			if( VerifyUtil.isNotBlank( query.getMobile() ) )
			{
				fromSql += " AND bean.MOBILE LIKE :mobile";
				
				
				values.put( "mobile", "%" + query.getMobile() + "%" );
			}
			if( null != query.getNickName() )
			{
				fromSql += " AND bean.NICK_NAME LIKE :nickName";
				values.put( "nickName", "%" + query.getNickName() + "%" );
			}
		}
		
		
		if( page.isAutoCount() )
		{
			page.setTotalCount(
					DataAccessUtils.uniqueResult(
							VerifyUtil.isEmpty( values ) ?
								getJdbcTemplate().query(
										"SELECT COUNT(*) " + fromSql
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								: getNamedParameterJdbcTemplate().query(
										"SELECT COUNT(*) " + fromSql
										, values
										, new SingleColumnRowMapper<Long>(Long.class)
										)
								)
					);
		}
		String pageSql = pageSql( page );
		page.setResult(
				VerifyUtil.isEmpty( values ) ?
					getJdbcTemplate().query(
							selectSql + fromSql + " ORDER BY bean.ID ASC " + pageSql
							, new BeanPropertyRowMapper<MemberVo>(MemberVo.class)
							)
					: getNamedParameterJdbcTemplate().query(
							selectSql + fromSql + " ORDER BY bean.ID ASC " + pageSql
							, values
							, new BeanPropertyRowMapper<MemberVo>(MemberVo.class)
							)
					);
	}
	
	@Override
	protected SimpleQuerySql querySql( Member query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "ACCOUNT", query.getAccount() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return Member.TABLE_NAME;
	}


    

   

}