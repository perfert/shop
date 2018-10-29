/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mas.core.service.ServiceException;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.service.impl.SmsConfigService;
import com.mas.user.service.MemberService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.common.PwdDto;

/**
 * SMS.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( PwdCtr.URI_PREFIX )
public class PwdCtr extends JsonCtr<PwdDto>{
    
    public static final String URI_PREFIX = JsonCtr.prefix + "/pwd";;
	
    //验证密码
    @RequestMapping( value = "verify", method = RequestMethod.POST )
    public void verify(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PwdDto.class,log,new DoYourBuniness<PwdDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PwdDto dto) {
                boolean success = configService.checkCode(SmsCode.TYPE_FORGET, dto.getCode(), dto.getPhone(), dto.getCaptcha());
                if(!success)
                    throw new ServiceException( "code.error" );
                
                String key = memberService.updateSafeKey(dto.getCode(), dto.getPhone());
                if(StringUtils.isNotEmpty(key)){
                    JSONObject result = new JSONObject();
                    result.put("key", key);
                    result.put("phone", dto.getPhone());
                    result.put("code", dto.getCode());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,"result.success",dto.getLang());
                }else
                    error(jsonObject,"result.error",dto.getLang());
            }
        });
    }
    
    //设置新密码
    @RequestMapping( value = "setting", method = RequestMethod.POST )
    public void setting(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PwdDto.class,log,new DoYourBuniness<PwdDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PwdDto dto){
                boolean success = memberService.settingPassword(dto.getCode(), dto.getPhone(),dto.getKey(),dto.getPassword());
                if(success){
                    success(jsonObject,"result.success",dto.getLang());
                }else
                    error(jsonObject,"result.error",dto.getLang());
            }
        });
    }
    
	
	@Autowired private MemberService memberService;
	@Autowired private SmsConfigService configService;
	
	private static final Logger log = LogManager.getLogger(PwdCtr.class);
}