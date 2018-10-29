/**    
 * 文件名：AdPositionQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.app.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.domain.entity.app.AdPosition;
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.repository.query.app.AdPositionQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class AdPositionQuerySql extends QuerySql<AdPosition> implements AdPositionQueryDao{

    @Override
    public List<Ad> getAdListBySign(String sign) {
        String sql = "SELECT bean.* FROM " + Ad.TABLE_NAME + " bean LEFT JOIN " + tableName() + " p ON bean.POSITION_ID = p.id WHERE p.SIGN = ? " ;
        return getJdbcTemplate().query(
                sql,new Object[] { sign }
                , new BeanPropertyRowMapper<Ad>(Ad.class)
                );
    }
    
    @Override
    public List<AdPosition> queryAll() {
        return getJdbcTemplate().query(
                "SELECT * FROM " + tableName()
                , new BeanPropertyRowMapper<AdPosition>(AdPosition.class)
                );
    }

    @Override
    protected SimpleQuerySql querySql(AdPosition query) {
        return  new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "NAME", query.getName() );
    }

    @Override
    protected String tableName() {
        return AdPosition.TABLE_NAME;
    }

    @Override
    public boolean exit(String sign) {
        String hql = " select COUNT(*) FROM " + tableName() + " bean where bean.sign = ?";
        long count = DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(hql, new Object[] { sign }, new SingleColumnRowMapper<Long>(Long.class)) );
        return count > 0 ? true : false;
    }

    
}
