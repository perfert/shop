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

import com.mas.common.file.util.FileUtil;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.repository.dao.app.AdDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class AdSql extends CrudSql<Ad> implements AdDao{

    @Override
    protected InsertSql insertSql( Ad bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "POSITION_ID", bean.getPositionId() )
                    .addField( "TITLE", bean.getTitle() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "FILE_PATH", bean.getFilePath() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "URL", bean.getUrl() )
                    .addField( "BEGIN_DATE", bean.getBeginDate() )
                    .addField( "END_DATE", bean.getEndDate() );
    }

    @Override
    protected UpdateSql updateSql( Ad bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "POSITION_ID", bean.getPositionId() )
                    .addField( "TITLE", bean.getTitle() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "FILE_PATH", bean.getFilePath() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "URL", bean.getUrl() )
                    .addField( "BEGIN_DATE", bean.getBeginDate() )
                    .addField( "END_DATE", bean.getEndDate() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    public boolean delete( Object id ){
        deleteFile(id);
        return 0 <
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE ID = ?"
                    , new Object[] { id }
                    );
    }
    
    private void deleteFile(Object articleId){
        Ad ad = get(articleId);
        FileUtil.remove(ad.getFilePath());
    }
    
    @Override
    protected String tableName() {
        return Ad.TABLE_NAME;
    }

}
