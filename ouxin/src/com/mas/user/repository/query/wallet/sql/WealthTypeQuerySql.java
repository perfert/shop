/**    
 * 文件名：WealthQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.wallet.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.query.wallet.WealthTypeQueryDao;
/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WealthTypeQuerySql extends QuerySql<WealthType> implements WealthTypeQueryDao{

    @Override
    protected SimpleQuerySql querySql(WealthType query) {
        return null;
    }

    @Override
    protected String tableName() {
        return WealthType.TABLE_NAME;
    }
    
    @Override
    public List<WealthType> getAll() {
        String selectSql = "SELECT bean.* FROM " + tableName() + " bean ORDER BY bean.ID ASC ";
        return getJdbcTemplate().query(
                selectSql
                , new Object[]{}
                , new BeanPropertyRowMapper<WealthType>(WealthType.class)
                );
    }
    
    @Override
    public List<WealthType> getAllContainWealth(String mid) {
        String selectSql = "SELECT bean.*,w.CASH AS BALANCE FROM " + tableName() + " bean LEFT JOIN " + Wealth.TABLE_NAME + " w ON bean.ID = w.WEALTH_TYPE " +
                " WHERE w.MEMBER_ID =  ? " +
                "  ORDER BY bean.ID ASC ";
        return getJdbcTemplate().query(
                selectSql
                , new Object[]{mid}
                , new BeanPropertyRowMapper<WealthType>(WealthType.class)
                );
    }
    
    @Override
    public WealthType getWealth(String memberId, String wealthTypeId) {
        String sql = "SELECT bean.*,w.CASH AS BALANCE FROM " + tableName() + " bean LEFT JOIN " + Wealth.TABLE_NAME + " w ON bean.ID = w.WEALTH_TYPE " +
                " WHERE bean.ID = ? AND w.MEMBER_ID =  ? " +
                "  ORDER BY bean.ID ASC ";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql
                        , new Object[] { wealthTypeId,memberId }
                        , new BeanPropertyRowMapper<WealthType>(WealthType.class)
                        ));
    }
    
    @Override
    public void queryPage( Page page, WealthType query )
    {
        String selectSql = "SELECT bean.* ";
        String fromSql = "FROM " + tableName() + " bean WHERE 1=1 ";
        Map<String, Object> values = new HashMap<String, Object>();
        if( null != query )
        {
            if( null != query.getName() )
            {
                fromSql += " AND bean.NAME LIKE :name";
                values.put( "name", "%" + query.getName() + "%" );
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
                            selectSql + fromSql + " ORDER BY bean.ID DESC " + pageSql
                            , new BeanPropertyRowMapper<WealthType>(WealthType.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            selectSql + fromSql + " ORDER BY bean.ID DESC " + pageSql
                            , values
                            , new BeanPropertyRowMapper<WealthType>(WealthType.class)
                            )
                    );
    }

    @Override
    public boolean isEnable(String wealthType) {
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT COUNT(bean.ID) FROM " + tableName() + " bean WHERE  bean.ID = ? AND bean.STATE = ?  "
                                , new Object[] {wealthType, 1 }
                                , new SingleColumnRowMapper<Long>(Long.class)
                                )
                        );
    }

    

    

    

    /*
    @Override
    public boolean exixt(String mid) {
        return 0 < DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                "SELECT COUNT(*) FROM " + tableName() + " WHERE MEMBER_ID = ? "
                , new Object[] { mid }
                , new SingleColumnRowMapper<Long>(Long.class)
                ));
    }
*/
 
}