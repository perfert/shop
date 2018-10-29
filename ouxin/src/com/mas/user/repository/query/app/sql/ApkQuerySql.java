/**    
 * 文件名：ArticleQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
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
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.repository.query.app.ApkQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class ApkQuerySql extends QuerySql<Apk> implements ApkQueryDao{

    @Override
    public List<Apk> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Apk query) {
        return  new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "VERSION_NAME", query.getVersionName() );
    }

    @Override
    protected String tableName() {
        return Apk.TABLE_NAME;
    }


    @Override
    public boolean isUnique(double version) {
        String hql = " SELECT bean.* FROM " + tableName() + " bean where bean.VERSION = ?";
        Apk apk =  DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(hql, new Object[] { version }, new BeanPropertyRowMapper<Apk>(Apk.class) ) );
        return null != apk ? false : true;
    }

    @Override
    public Apk getTheLatestApk() {
        double max = getMaxVersion();
        if(max > 0 )
            return DataAccessUtils.uniqueResult(getJdbcTemplate().query(" SELECT bean.* FROM " + tableName() + " bean where bean.VERSION = ?", new Object[]{max},new BeanPropertyRowMapper<Apk>(Apk.class) ) );
        else
            return null;
    }

    @Override
    public double getMaxVersion() {
        String hql = " SELECT MAX(bean.VERSION) FROM " + tableName() +  " bean ";
        Double result = DataAccessUtils.uniqueResult(getJdbcTemplate().query(hql , new SingleColumnRowMapper<Double>(Double.class) ) );
        return null != result ? result : 0;
    }
    
    
}
