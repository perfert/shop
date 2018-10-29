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
import com.mas.user.domain.entity.chat.MsgCollectionContent;
import com.mas.user.repository.query.chat.MsgCollectionContentQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    MsgCollectionQuerySql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgCollectionContentQuerySql extends QuerySql<MsgCollectionContent> implements MsgCollectionContentQueryDao{

    @Override
    public List<MsgCollectionContent> queryAll(Object collectionId) {
        String selectSql =  " SELECT bean.* FROM " + tableName() + " bean  WHERE bean.COLLECTION_ID =?  ORDER BY bean.PRIORITY ASC ";
        return getJdbcTemplate().query(
                selectSql   
                ,   new Object[] { collectionId } 
                , new BeanPropertyRowMapper<MsgCollectionContent>(MsgCollectionContent.class)
                ); 
    }
    
    @Override
    protected SimpleQuerySql querySql(MsgCollectionContent query) {
        // TODO Auto-generated method stub
        return null;
    }
    

    @Override
    protected String tableName() {
        return MsgCollectionContent.TABLE_NAME;
    }


   

}
