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

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.Sign;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.repository.dao.chat.SignMemberDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class SignMemberSql extends CrudSql<SignMember> implements SignMemberDao{
    
    @Override
    public boolean deleteExcluded(List<Object> ids, Object username,Object friends) {
        String queryString = "SELECT bean.ID from "+ tableName() + " bean LEFT JOIN " + Sign.TABLE_NAME + " sign ON bean.SIGN_ID = sign.ID " +
                " WHERE sign.USERNAME = ? AND bean.USERNAME = ? ";
        List<Object> allIds = getJdbcTemplate().query(
                queryString,new Object[]{username,friends}
                , new SingleColumnRowMapper<Object>(Object.class)
                );
        
        for(Object o : allIds){
            boolean isDelete = true;
            for(Object exist:ids){
                if(o.toString().equalsIgnoreCase(exist.toString())){
                    isDelete = false;
                    break;
                }
            }
            if(isDelete){
                delete(o);
            }
                
        }
        return true;
    }
    
    @Override
    protected InsertSql insertSql(SignMember bean) {
        return
                new InsertSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "USERNAME", bean.getUsername() )
                    .addField( "SIGN_ID", bean.getSignId() );
    }
    
    @Override
    protected UpdateSql updateSql(SignMember bean) {
        return
                new UpdateSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "USERNAME", bean.getUsername() )
                    .addField( "SIGN_ID", bean.getSignId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return SignMember.TABLE_NAME;
    }

    
    
}
