/**    
 * 文件名：CertificateQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.repository.query.sql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.Certificate;
import com.mas.shops.repository.query.CertificateQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
@Repository
public class CertificateQuerySql extends QuerySql<Certificate> implements CertificateQueryDao{
    
    
    @Override
    protected SimpleQuerySql querySql( Certificate query )
    {
        return
                new SimpleQuerySql( tableName() )
                    .andLkWhereIfNotBlankValue( "NAME", query.getName() )
                    .addDesc( "ID" );
    }

    @Override
    protected String tableName()
    {
        return Certificate.TABLE_NAME;
    }

    @Override
    public Certificate getByShopsId(String id) {
        return DataAccessUtils.uniqueResult( getJdbcTemplate().query(
                "SELECT bean.* FROM " + tableName() + " bean WHERE SHOPS_ID = ? "
                , new Object[] { id }
                , new BeanPropertyRowMapper<Certificate>(Certificate.class)
                )
                );
    }


   

}