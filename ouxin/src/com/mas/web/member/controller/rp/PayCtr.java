/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.rp;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mas.user.domain.entity.vo.RpPayVo;
import com.mas.user.service.wallet.WealthService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.rp.PayDto;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * 红包Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(PayCtr.URI_PREFIX)
public class PayCtr extends JsonCtr<PayDto>{
	
    public static final String URI_PREFIX = RedpacketCtr.URI_PREFIX;
    private static final Logger log = LogManager.getLogger( PayCtr.class );
    
    @Autowired private WealthService wealthService;
    
    
    /**
     * 支付
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "pay", method = RequestMethod.POST )
    public void pay(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,PayDto.class,log,new DoYourBuniness<PayDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,PayDto dto)  {
                JSONObject result = new JSONObject();
                RpPayVo vo = wealthService.payRp(dto.getMid(),dto.getWealthTypeId(),dto.getRpId(),dto.getPayPassword());
                if(null != vo){
                    if(vo.getCode() == 200){
                        jsonObject.put( KEY_RESULT, result );
                        success(jsonObject,null,dto.getLang());
                    }else if(vo.getCode() == 201){
                        Locale local = null;
                        if(dto.getLang().equalsIgnoreCase("zh")){
                            local = Locale.CHINA;
                        }else{
                            local = Locale.ENGLISH;
                        }
                        jsonObject.put( KEY_STATUS, vo.getCode() );
                        jsonObject.put( KEY_MESSAGE,SpringUtils.getApplicationContext().getMessage("rp.paypwd.error", null, local));
                    }else{
                        error(jsonObject,null,dto.getLang());
                    }
                }else{
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
}