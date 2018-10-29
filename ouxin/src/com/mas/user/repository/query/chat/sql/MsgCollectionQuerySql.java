/**    
 * 文件名：MsgRecordQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat.sql;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.MsgCollection;
import com.mas.user.repository.query.chat.MsgCollectionQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    MsgCollectionQuerySql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgCollectionQuerySql extends QuerySql<MsgCollection> implements MsgCollectionQueryDao{

    @Override
    public List<MsgCollection> queryByMemberUsername(String memberId, int pageNo, int pageSize) {
        String selectSql =  " SELECT bean.*,u.AVATAR AS AVATAR,u.NICK_NAME AS NICK FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.COVER_MEMBER_ID = u.ID " + 
        		" WHERE bean.MEMBER_ID =?  ORDER BY bean.CREATE_DATE DESC " + pageSql(pageNo, pageSize);
        return getJdbcTemplate().query(
                selectSql
                ,   new Object[] { memberId } 
                , new BeanPropertyRowMapper<MsgCollection>(MsgCollection.class)
                ); 
    }
    
    @Override
    protected SimpleQuerySql querySql(MsgCollection query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getId() );
    }

    @Override
    protected String tableName() {
        return MsgCollection.TABLE_NAME;
    }

}
