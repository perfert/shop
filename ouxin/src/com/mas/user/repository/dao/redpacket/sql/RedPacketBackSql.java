/**    
 * 文件名：RedPacketBackSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-21    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.rp.RedPacketBack;
import com.mas.user.repository.dao.redpacket.RedPacketBackDao;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-21
 */
@Repository
public class RedPacketBackSql extends CrudSql<RedPacketBack> implements RedPacketBackDao {

    @Override
    protected InsertSql insertSql(RedPacketBack bean) {

        return new InsertSql(tableName())
                .addField("SN", bean.getSn())
                .addField("STATUS", bean.getStatus())
                .addField("CASH", bean.getCash())
                .addField("RP_ID", bean.getRpId())
                .addField("MEMBER_ID", bean.getMemberId());
    }

    @Override
    protected UpdateSql updateSql(RedPacketBack bean) {
        return null;
    }

    @Override
    protected String tableName() {
        return RedPacketBack.TABLE_NAME;
    }

}
