/**    
 * 文件名：ArticleSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.web.manage.controller.chat;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.wallet.WealthType;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class FileSql extends CrudSql<MFile> implements FileDao{
    
    @Override
    protected InsertSql insertSql( MFile bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "IS_INSERT", bean.getInsert() ) 
                    .addField( "NAME", bean.getName() ) ;
    }

    @Override
    protected UpdateSql updateSql( MFile bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "IS_INSERT", bean.getInsert() ) 
                    .addField( "NAME", bean.getName() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return MFile.TABLE_NAME;
    }

    @Override
    public boolean exist(String name) {
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT COUNT(bean.ID) FROM " + tableName() + " bean WHERE bean.NAME = ? " 
                                , new Object[] { name }
                                , new SingleColumnRowMapper<Long>(Long.class)
                                )
                        );
    }

    @Override
    public void deleteByName(String name) {
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE NAME = ?"
                    , new Object[] { name }
                    );
    }

    @Override
    public List<String> getAll() {
        String selectSql = "SELECT bean.NAME FROM " + tableName() + " bean ORDER BY bean.ID ASC ";
        return getJdbcTemplate().query(
                selectSql
                , new Object[]{}
                , new SingleColumnRowMapper<String>(String.class)
                );
    }
   
}
