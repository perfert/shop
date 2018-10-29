/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.wallet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.user.domain.entity.rp.RedPacketConfig;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.service.redpacket.RedPacketConfigService;
import com.mas.user.service.wallet.WealthTypeService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.wallet.RpconfigDto;
import com.mas.web.manage.controller.dto.wallet.WealthTypeDto;

/**
 * 财富类型Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(RpConfigCtr.URI_PREFIX)
public class RpConfigCtr extends ManageCtr<RedPacketConfig, RpconfigDto> {

    public static final String URI_PREFIX = "manage/wallet/rp_config";
    
    
    @Override
    protected BaseService<RedPacketConfig> service() {
        return service;
    }

    @Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return URI_PREFIX;
    }

    @Override
    protected Logger log() {
        return log;
    }

    @Autowired
    private RedPacketConfigService service;

    private static final Logger log = LogManager.getLogger(RpConfigCtr.class);
}