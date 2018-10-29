/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.dto.news;

import com.mas.user.domain.entity.news.Article;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * ArticleCtrDto  controller DTO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author YIXUAN
 */
public class ArticleCtrDto extends BaseCtrDto<Article>
{
    private String[] images;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
    
    
}