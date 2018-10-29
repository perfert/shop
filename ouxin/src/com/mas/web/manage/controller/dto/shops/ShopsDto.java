/**    
 * 文件名：ShopsDto.java    
 *    
 * 版本信息：    
 * 日期：2018-3-23    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.web.manage.controller.dto.shops;

import com.mas.shops.domain.entity.Certificate;
import com.mas.shops.domain.entity.Shops;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-23  
 */
public class ShopsDto extends BaseCtrDto<Shops>{
    
    private Certificate certificate;
    
    private Integer verify;
    
    private String reason;
    
    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
    
    
}
