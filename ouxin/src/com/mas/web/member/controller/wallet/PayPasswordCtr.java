/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.wallet;


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
import com.mas.user.service.MemberService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.wallet.PayPasswordtDto;

/**
 * 支付密码Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(PayPasswordCtr.URI_PREFIX)
public class PayPasswordCtr extends JsonCtr<PayPasswordtDto>{
	
    public static final String URI_PREFIX = WalletCtr.URI_PREFIX + "/pwd";
    private static final Logger log = LogManager.getLogger( PayPasswordCtr.class );
    

    @Autowired private MemberService memberService;
    
    //第一次设置密码
    @RequestMapping( value = "first", method = RequestMethod.POST )
    public void first(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PayPasswordtDto.class,log,new DoYourBuniness<PayPasswordtDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PayPasswordtDto dto)  {
                boolean success = memberService.settingPayPassword(dto.getMid(),dto.getPayPassword());
                if(success){
                    success(jsonObject,"result.success",dto.getLang());
                }else
                    error(jsonObject,"result.error",dto.getLang());
            }
        });
    }
    
    //验证密码 2
    @RequestMapping( value = "verifycode", method = RequestMethod.POST )
    public void code(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PayPasswordtDto.class,log,new DoYourBuniness<PayPasswordtDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PayPasswordtDto dto) {
                String key = memberService.verifyPayPassword(dto.getCode(), dto.getPhone(), dto.getCaptcha());
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
    
    //设置密码
    @RequestMapping( value = "setting", method = RequestMethod.POST )
    public void setting(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PayPasswordtDto.class,log,new DoYourBuniness<PayPasswordtDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PayPasswordtDto dto) {
                boolean success = memberService.settingPayPassword(dto.getMid(),dto.getKey(),dto.getPayPassword());
                if(success){
                    success(jsonObject,"result.success",dto.getLang());
                }else
                    error(jsonObject,"result.error",dto.getLang());
            }
        });
    }
    
    //验证更新密码
    @RequestMapping( value = "verify", method = RequestMethod.POST )
    public void pwd(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PayPasswordtDto.class,log,new DoYourBuniness<PayPasswordtDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PayPasswordtDto dto){
                String key = memberService.verifyPayPassword(dto.getMid(),dto.getPayPassword());
                if(StringUtils.isNotEmpty(key)){
                    JSONObject result = new JSONObject();
                    result.put("key", key);
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,"result.success",dto.getLang());
                }else
                    error(jsonObject,"result.error",dto.getLang());
            }
        });
    }
    
    //设置更新密码
    @RequestMapping( value = "update", method = RequestMethod.POST )
    public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody String body){
        doInCallBack(response,body,PayPasswordtDto.class,log,new DoYourBuniness<PayPasswordtDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, PayPasswordtDto dto) {
                boolean success = memberService.settingPayPassword(dto.getMid(),dto.getKey(),dto.getPayPassword());
                if(success){
                    success(jsonObject,"result.success",dto.getLang());
                }else
                    error(jsonObject,"result.error",dto.getLang());
            }
        });
    }
   
}