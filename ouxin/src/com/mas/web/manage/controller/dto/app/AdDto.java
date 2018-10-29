/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.dto.app;

import java.util.List;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.domain.entity.app.Ad.Type;
import com.mas.user.domain.entity.app.AdPosition;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * AdDto  controller DTO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author YIXUAN
 */
public class AdDto extends BaseCtrDto<Ad>
{
    private List<AdPosition> adPositionList;
    
    private Type[] types;

    public List<AdPosition> getAdPositionList() {
        return adPositionList;
    }

    public void setAdPositionList(List<AdPosition> adPositionList) {
        this.adPositionList = adPositionList;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    
}