/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.wallet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mas.core.service.BaseService;
import com.mas.core.service.ServiceException;
import com.mas.security.domain.vo.Principal;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.service.wallet.WealthService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.member.MemberCtrDto;
import com.mas.web.manage.controller.dto.wallet.WealthDto;
import com.mas.web.util.JsonUtil;

/**
 * 财富Ctr
 * 
 * @version v1.00
 * @since JDK 1.7
 * @author MAS
 */
@Controller
@RequestMapping(WealthCtr.URI_PREFIX)
public class WealthCtr extends ManageCtr<Wealth, WealthDto> {

    public static final String URI_PREFIX = "manage/wallet/wealth";

    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    public void recharge(MemberCtrDto dto, HttpServletRequest request, HttpServletResponse response, @PathVariable
    Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        try {
            /*Principal user = (Principal) SecurityUtils.getSubject().getPrincipal(); 
            service.recharge(user.getId(),dto.getId(), dto.getCash());
            result.put("statusCode", 200);
            result.put("msg", "充值成功！");*/
        } catch (Exception ex) {
            if (!(ex instanceof ServiceException))
                log.error(ex.getMessage());
            result.put("statusCode", 0);
            result.put("msg", ex instanceof ServiceException ? ex.getMessage() : "充值失败！");
        }
        JsonUtil.renderJson(response, result);
    }
    
    /**
     * 根据页码, 页行, 分页检索数据.
     * 
     * @param pageNo 页码.
     * @param dto Controller DTO Context.
     * @param model 模型参数.
     * @param params {@link PathVariable} 参数集
     * 
     * @return view uri
     */
    @RequestMapping( "list/{pageNo}")
    public String page(@PathVariable int pageNo, WealthDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
    {
        try {
            service.queryPage( dto.getPager(pageNo), dto.getQuery() );
        } catch (Exception ex) {
            handlerException( ex, log(), model );
        }
        return VIEW_LIST( dto, model, params );
    }
    
    @Override
    protected BaseService<Wealth> service() {
        return service;
    }

    @Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return URI_PREFIX;
    }

    @Override
    protected String REDIRECT_VIEW_PREFIX(Map<String, Object> params) {
        return "manage/news" + SPLIT + pathValue(params, "article") + SPLIT + "comment";
    }

    @Override
    protected Logger log() {
        return log;
    }

    @Autowired
    private WealthService service;

    private static final Logger log = LogManager.getLogger(WealthCtr.class);
}