/**    
 * 文件名：SignSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.Sign;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.repository.dao.chat.SignDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class SignSql extends CrudSql<Sign> implements SignDao{
    
    @Override
    public boolean updateSign(Object signId, String detail, int count) {
        return 0 <
                getJdbcTemplate().update(
                    "UPDATE " + tableName() + " SET DETAIL_NAME = ?,NUM = ? WHERE  ID = ?"
                    , new Object[] { detail,count, signId }
                    );
    }
    
    @Override
    public boolean delete(String signId, String userId) {
        getJdbcTemplate().update("DELETE FROM " + SignMember.TABLE_NAME + " WHERE SIGN_ID = ?", new Object[] { signId });
        return 0 < getJdbcTemplate().update("DELETE FROM " + tableName() + " WHERE ID = ? AND MEMBER_ID = ? ", new Object[] { signId, userId});
    }
    
    @Override
    public boolean deleteSignMemberBySignId(String signId) {
        return  0 < getJdbcTemplate().update("DELETE FROM " + SignMember.TABLE_NAME + " WHERE SIGN_ID = ?", new Object[] { signId });
    }


    @Override
    protected InsertSql insertSql(Sign bean) {
        return
                new InsertSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "USERNAME", bean.getUsername() )
                    .addField( "NUM", bean.getNum() )
                    .addField( "DETAIL_NAME", bean.getDetailName() );
    }
    
    @Override
    protected UpdateSql updateSql(Sign bean) {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "USERNAME", bean.getUsername() )
                    .addField( "NUM", bean.getNum() )
                    .addField( "DETAIL_NAME", bean.getDetailName() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Sign.TABLE_NAME;
    }

    


    
}
