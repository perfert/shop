/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.domain.entity.vo;

import java.math.BigDecimal;

import com.mas.user.domain.entity.Member;

/**
 * 会员财富
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class MemberWealthVo extends Member {
    private static final long serialVersionUID = 1L;

    private BigDecimal cash;

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

}