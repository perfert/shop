/**    
 * 文件名：RedPacketSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket.sql;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.rp.RedPacketNum;
import com.mas.user.repository.dao.redpacket.RedPacketNumDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketNumSql extends CrudSql<RedPacketNum> implements RedPacketNumDao{

    @Override
    public int[] insertList(final List<RedPacketNum> list) {
        List<Object[]> batch = new ArrayList<Object[]>();
        for (int i = 0; i < list.size(); i++) {
            RedPacketNum num = list.get(i);
            Object[] values = new Object[] {
                    num.getPriority(),
                    num.getState(),
                    num.getCreateDate(),
                    num.getModifyDate(),
                    num.getCash(),
                    num.getValid(),
                    num.getIsLuck(),
                    num.getRpId()};
            batch.add(values);
        }
        int[] updateCounts = getJdbcTemplate().batchUpdate(
                " insert into " + tableName() + "  (ID,PRIORITY,STATE,CREATE_DATE,MODIFY_DATE,CASH,VALID,IS_LUCK,RP_ID)" +
        		" values (null,?,?,?,?,?,?,?,?) ",
                batch);
        return updateCounts;
    }
    
    

    @Override
    protected InsertSql insertSql( RedPacketNum bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "VALID", bean.getValid() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "IS_LUCK", bean.getIsLuck() )
                    .addField( "RP_ID", bean.getRpId() );
    } 
    
    @Override
    protected UpdateSql updateSql( RedPacketNum bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "VALID", bean.getValid() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "IS_LUCK", bean.getIsLuck() )
                    .addField( "RP_ID", bean.getRpId() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return RedPacketNum.TABLE_NAME;
    }

   
    
}
