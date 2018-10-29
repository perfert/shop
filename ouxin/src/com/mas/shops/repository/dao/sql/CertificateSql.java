/**    
 * 文件名：CertificateSql.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.Certificate;
import com.mas.shops.repository.dao.CertificateDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
@Repository
public class CertificateSql  extends CrudSql<Certificate> implements CertificateDao {

    @Override
    protected InsertSql insertSql( Certificate bean )
    {

        return 
                new InsertSql( tableName() )  
                    .addField( "NAME", bean.getName())
                    .addField( "BUSINESS_SCOPE", bean.getBusinessScope() )
                    .addField( "LEGAL_PERSON", bean.getLegalPerson() )
                    .addField( "CARD_NO", bean.getCardNo() )
                    .addField( "BUSINESS_LICENSE", bean.getBusinessLicense() )
                    .addField( "CARD_REVERSE_PATH", bean.getCardReversePath() )
                    .addField( "CARD_FACE_PATH", bean.getCardFacePath() )
                    .addField( "BUSINESS_LICENSE_PATH", bean.getBusinessLicensePath() )
                    .addField( "ORGANIZATION_CODE", bean.getOrganizationCode() )
                    .addField( "SHOPS_ID", bean.getShopsId() );
    }

    @Override
    protected UpdateSql updateSql( Certificate bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "BUSINESS_SCOPE", bean.getBusinessScope() )
                    .addField( "LEGAL_PERSON", bean.getLegalPerson() )
                    .addField( "CARD_NO", bean.getCardNo() )
                    .addField( "BUSINESS_LICENSE", bean.getBusinessLicense() )
                      .addField( "CARD_REVERSE_PATH", bean.getCardReversePath() )
                      .addField( "CARD_FACE_PATH", bean.getCardFacePath() )
                       .addField( "BUSINESS_LICENSE_PATH", bean.getBusinessLicensePath() )
                    .addField( "ORGANIZATION_CODE", bean.getOrganizationCode() )
                    .addField( "SHOPS_ID", bean.getShopsId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Certificate.TABLE_NAME;
    }
    
    

}