/**    
 * 文件名：SnSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-13    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.wallet.sql;

import java.util.Date;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.util.DateTimeUtil;
import com.mas.reposiroty.springjdbc.dao.BaseSpringJdbcDao;
import com.mas.user.domain.entity.rp.Sn;
import com.mas.user.repository.dao.wallet.SnDao;


/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-13  
 */
@Repository
public class SnSql extends BaseSpringJdbcDao implements SnDao, InitializingBean {

    private HiloOptimizer orderHiloOptimizer;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        orderHiloOptimizer = new HiloOptimizer(100);
    }

    @Override
    public String generate() {
        return orderHiloOptimizer.generate();
    }

    private long getLastValue() {
        int type = 0;
        long value = DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                "SELECT LAST_VALUE FROM " + Sn.TABLE_NAME + " WHERE TYPE = ? "
                , new Object[] {type}
                , new SingleColumnRowMapper<Long>(Long.class)
                ));
        getJdbcTemplate().update(
                " UPDATE " + Sn.TABLE_NAME + " bean " +
                " SET bean.LAST_VALUE = ? " +
                " WHERE bean.TYPE = ? "
                , new Object[] {value + 1,type}
                );
        return value;
    }

    /**
     * 高低位算法
     */
    private class HiloOptimizer {

        private int maxLo;
        private int lo;
        private long hi;
        private long lastValue;

        public HiloOptimizer(int maxLo) {
            this.maxLo = maxLo;
            this.lo = maxLo + 1;
        }

        public synchronized String generate() {
            if (lo > maxLo) {
                lastValue = getLastValue();
                lo = lastValue == 0 ? 1 : 0;
                hi = lastValue * (maxLo + 1);
            }
            try {
                return DateTimeUtil.format(DateTimeUtil.YYYYMMDD, new Date()) + (hi + lo++);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return String.valueOf(hi + lo++);
        }
    }
}
