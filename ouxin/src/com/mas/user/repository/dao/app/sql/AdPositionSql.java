/**    
 * 文件名：ArticleSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.app.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.app.AdPosition;
import com.mas.user.domain.entity.news.Comment;
import com.mas.user.repository.dao.app.AdPositionDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class AdPositionSql extends CrudSql<AdPosition> implements AdPositionDao{
    
    @Override
    protected InsertSql insertSql( AdPosition bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "WIDTH", bean.getWidth() )
                    .addField( "HEIGHT", bean.getHeight() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "SIGN", bean.getSign() )
                    .addField( "MAX_AD", bean.getMaxAd() ) ;
    }

    @Override
    protected UpdateSql updateSql( AdPosition bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "WIDTH", bean.getWidth() )
                    .addField( "HEIGHT", bean.getHeight() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "SIGN", bean.getSign() )
                    .addField( "MAX_AD", bean.getMaxAd() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    public boolean delete( Object id ){
        return 0 <
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE ID = ?"
                    , new Object[] { id }
                    );
    }
    
    @Override
    protected String tableName() {
        return AdPosition.TABLE_NAME;
    }
}
