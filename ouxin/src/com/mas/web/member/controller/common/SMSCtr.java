/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.common;

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
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.service.impl.SmsConfigService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.common.SMSDto;

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
@RequestMapping( SMSCtr.URI_PREFIX )
public class SMSCtr extends JsonCtr<SMSDto>{
    
    public static final String URI_PREFIX = JsonCtr.prefix + "/sms";;
	
	@RequestMapping( value = "regist", method = RequestMethod.POST )
	public void registSMS(@RequestBody String body, HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,SMSDto.class,log,new DoYourBuniness<SMSDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, SMSDto dto)  {
                boolean success = configService.sendCode(SmsCode.TYPE_REGISTER, dto.getCode(), dto.getPhone());
                if( success ){
                    success(jsonObject,"result.success",dto.getLang());
                } else {
                    error(jsonObject, "result.error",dto.getLang());
                }
            }
        });
	}
	
	@RequestMapping( value = "forget", method = RequestMethod.POST )
	public void forget(@RequestBody String body, HttpServletRequest request, HttpServletResponse response)
	{
	    doInCallBack(response,body,SMSDto.class,log,new DoYourBuniness<SMSDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, SMSDto dto)  {
                boolean success = configService.sendCode(SmsCode.TYPE_FORGET, dto.getCode(), dto.getPhone());
                
                if( success ){
                    success(jsonObject,"result.success",dto.getLang());
                } else {
                    error(jsonObject, "result.error",dto.getLang());
                }
            }
        });	   
	    
	}
	
	@RequestMapping( value = "paywd", method = RequestMethod.POST )
    public void paywd(@RequestBody String body, HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,SMSDto.class,log,new DoYourBuniness<SMSDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, SMSDto dto)  {
                boolean success = configService.sendCode(SmsCode.TYPE_PAY_PASSWORD, dto.getCode(), dto.getPhone());
                if( success ){
                    success(jsonObject,"result.success",dto.getLang());
                } else {
                    error(jsonObject, "result.error",dto.getLang());
                }
            }
        });
    }
	
	
	@Autowired private SmsConfigService configService;
	
	private static final Logger log = LogManager.getLogger(SMSCtr.class);
}