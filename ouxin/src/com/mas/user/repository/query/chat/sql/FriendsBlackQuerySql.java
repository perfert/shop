/**    
 * 文件名：FriendsBlackQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.sql.util.Symbol;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.chat.FriendsBlack;
import com.mas.user.repository.query.chat.FriendsBlackQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    黑名单Sql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class FriendsBlackQuerySql extends QuerySql<FriendsBlack> implements FriendsBlackQueryDao{

    @Override
    public FriendsBlack getFriendsBlack(Object applyId, Object targetId) {
        SimpleQuerySql sql = new SimpleQuerySql( tableName() )
        .andWhere( "MEMBER_ID", Symbol.eq, targetId)
        .andWhere( "TARGET_ID", Symbol.eq, applyId);
        return DataAccessUtils.uniqueResult(getNamedParameterJdbcTemplate().query(
                sql.sql()
                , sql.values()
                , new BeanPropertyRowMapper<FriendsBlack>(FriendsBlack.class)
                ));
    }

    
    @Override
    public List<FriendsBlack> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(FriendsBlack query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getMemberId() )
                .andEqWhereIfNotBlankValue( "TARGET_ACCOUNT", query.getTargetAccount() );
    }

    @Override
    protected String tableName() {
        return FriendsBlack.TABLE_NAME;
    }

    
}
