/**    
 * 文件名：ArticleSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.app.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.repository.dao.app.ApkDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    ApkSql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class ApkSql extends CrudSql<Apk> implements ApkDao{

    @Override
    protected InsertSql insertSql( Apk bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "VERSION_NAME", bean.getVersionName() )
                    .addField( "VERSION", bean.getVersion() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "FILE_PATH", bean.getFilePath() ) ;
    }

    @Override
    protected UpdateSql updateSql( Apk bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "VERSION_NAME", bean.getVersionName() )
                    .addField( "VERSION", bean.getVersion() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "FILE_PATH", bean.getFilePath() ) 
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return Apk.TABLE_NAME;
    }

}
