/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.chat;

import java.util.List;

import com.mas.core.repository.dao.CrudDao;

/**
 * 会员
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface FileDao  extends CrudDao<MFile>
{

    boolean exist(String name);

    void deleteByName(String string);

    List<String> getAll();
	

}