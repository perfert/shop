/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller;

import com.mas.core.domain.entity.Entity;
import com.mas.web.springmvc.controller.CrudCtr;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * Abstract manage controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class ManageCtr<L extends Entity, D extends BaseCtrDto<L>> extends CrudCtr<L, D>
{
}