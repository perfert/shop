/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.third;

import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.core.service.ServiceException;
import com.mas.security.util.verify.ApiUtil;
import com.mas.shops.domain.vo.MortagagePayVo;
import com.mas.shops.domain.vo.PayRequestVo;
import com.mas.shops.domain.vo.ThirdPayResultVo;
import com.mas.shops.domain.vo.ThirdPayVo;
import com.mas.shops.service.ShopsService;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.service.MemberService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.AuthDto;
import com.mas.web.member.controller.dto.third.ThirdDto;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * 登录/注册
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(ThirdCtr.URI_PREFIX)
public class ThirdCtr extends JsonCtr<ThirdDto>{
    
    private static final Logger log = LogManager.getLogger( ThirdCtr.class );
    
    public static final String URI_PREFIX = JsonCtr.prefix + "/third";
    
    @Autowired private MemberService service;
    @Autowired private ShopsService shopsService;
    
    /**
     * 店铺获取token
     * @param body
     * @param request
     * @param response
     * @return
     */
	@RequestMapping( value = "apply", method = RequestMethod.POST )
    @ResponseBody
    public String auth(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
	    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(); 
        try {
            com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSON.parseObject(body);
            com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
            if(ApiUtil.check(THIRD_KEY, data)){
                String  token = data.getString("token");
                Member bean = service.getByToken(token);
                if (null != bean) {
                    result.put("username", bean.getUsername());
                    result.put("state", bean.getState());
                    result.put("userId", bean.getId());
                }
                jsonObject.put(KEY_STATUS, KEY_SUCCESS);
                jsonObject.put(KEY_RESULT, result);
                ApiUtil.encode(THIRD_KEY, jsonObject);
            }else{
                jsonObject.put(KEY_STATUS, LOGIN);
                ApiUtil.encode(THIRD_KEY, jsonObject);
            }
        } catch (Exception ex) {
        }
        return jsonObject.toString();
    }
	
	/**
     * 押金支付信息[app请求]
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "pay/mortgage/info", method = RequestMethod.POST )
    public void mortagageInfo(final @RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ThirdDto.class,log,new DoYourBuniness<ThirdDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ThirdDto dto) {
                if(!isLogin(body)){
                    error(jsonObject, "login.error",dto.getLang());
                }
                String data = dto.getPayData();
                com.alibaba.fastjson.JSONObject dataJson = com.alibaba.fastjson.JSON.parseObject(data);
                JSONObject result = new JSONObject();
                if(ApiUtil.check(THIRD_KEY, dataJson)){
                    PayRequestVo payRequest = com.alibaba.fastjson.JSON.parseObject(data,PayRequestVo.class);
                    payRequest.setName("支付压金");
                    if(StringUtils.isEmpty(payRequest.getName()))
                        throw new ServiceException("parameter.error");
                    ThirdPayVo vo = shopsService.getPayInfo(dto.getMid(),payRequest.getRateList());
                    List<WealthType> list = vo.getTypeList();
                    JSONArray typeJsonArray = new JSONArray();
                    if( null != list && list.size() > 0 ){
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject bean = new JSONObject(); 
                            WealthType type = list.get(i);
                            bean.put("id", type.getId());
                            bean.put("name", type.getName());
                            bean.put("domainUrl", type.getDomainUrl());
                            bean.put("isDefault", type.getIsDefault() ? "1" : "0");
                            bean.put("balance", type.getBalance());
                            bean.put("cost", type.getCost());
                            typeJsonArray.add(i, bean);
                        }
                    } 
                    result.put("isHasPwd", vo.getIsHasPwd());
                    result.put("name", payRequest.getName());
                    result.put("wealthTypeList", typeJsonArray);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "third.pay.error",dto.getLang());
                }
            }
        });
    }
	
	/**
     * 支付押金[第三方接口]
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "pay/mortgage/submit", method = RequestMethod.POST )
    @ResponseBody
    public void mortagageCreate(final @RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ThirdDto.class,log,new DoYourBuniness<ThirdDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ThirdDto dto)  {
                if(!isLogin(body)){
                    error(jsonObject, "login.error",dto.getLang());
                }
                JSONObject result = new JSONObject();
                MortagagePayVo vo = shopsService.mortagagePay(dto.getMid(),dto.getPayPassword(),dto.getWealthTypeId(),dto.getCash());
                if(null != vo){
                    if(vo.getCode() == 200){
                        result.put("mortagageId", vo.getMortagageId());
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
    
    /**
     * 退还押金[第三方接口]
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "pay/mortagage/back", method = RequestMethod.POST )
    @ResponseBody
    public String mortagageBack(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(); 
        try {
            com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
            if(ApiUtil.check(THIRD_KEY, com.alibaba.fastjson.JSON.parseObject(body))){
                ThirdDto dto = (ThirdDto) parseObject(body, ThirdDto.class);
                Object recordId = shopsService.mortagageBack(dto.getMid());
                if(null != recordId){
                    result.put("recordId", recordId);
                    jsonObject.put(KEY_STATUS, KEY_SUCCESS);
                    jsonObject.put(KEY_RESULT, result);
                }else{
                    jsonObject.put(KEY_STATUS, KEY_ERROR);
                }
            }else{
                jsonObject.put(KEY_STATUS, LOGIN);
            }
            ApiUtil.encode(THIRD_KEY, jsonObject);
        } catch (Exception ex) {
            jsonObject.put(KEY_STATUS, KEY_ERROR);
            jsonObject.put(KEY_MESSAGE, ex.getMessage());
            ApiUtil.encode(THIRD_KEY, jsonObject);
        }
        return jsonObject.toString();
    }
    
    /**
     * 店铺创建[第三方接口]
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "shops/create", method = RequestMethod.POST )
    @ResponseBody
    public String storeCreate(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(); 
        try {
            com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
            if(ApiUtil.check(THIRD_KEY, com.alibaba.fastjson.JSON.parseObject(body))){
                ThirdDto dto = (ThirdDto) parseObject(body, ThirdDto.class);
                Object shopsId = shopsService.createThirdShops(dto.getMid(),dto.getShopsId(),dto.getTitle(),dto.getLegalPerson(),dto.getCardNo(),dto.getMobile(),dto.getAddress(),dto.getLink(),dto.getLogoPath(),dto.getDescription(),dto.getName());
                if(null != shopsId){
                    result.put("shopsId", shopsId);
                    jsonObject.put(KEY_STATUS, KEY_SUCCESS);
                    jsonObject.put(KEY_RESULT, result);
                }else{
                    jsonObject.put(KEY_STATUS, KEY_ERROR);
                }
            }else{
                jsonObject.put(KEY_STATUS, LOGIN);
            }
            ApiUtil.encode(THIRD_KEY, jsonObject);
        } catch (Exception ex) {
            jsonObject.put(KEY_STATUS, KEY_ERROR);
            jsonObject.put(KEY_MESSAGE, ex.getMessage());
            ApiUtil.encode(THIRD_KEY, jsonObject);
        }
        return jsonObject.toString();
    }
    
    /**
     * 支付信息[app请求]
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "pay/order/info", method = RequestMethod.POST )
    public void order(final @RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ThirdDto.class,log,new DoYourBuniness<ThirdDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ThirdDto dto) {
                if(!isLogin(body)){
                    error(jsonObject, "login.error",dto.getLang());
                }
                String data = dto.getPayData();
                com.alibaba.fastjson.JSONObject dataJson = com.alibaba.fastjson.JSON.parseObject(data);
                JSONObject result = new JSONObject();
                if(ApiUtil.check(THIRD_KEY, dataJson)){
                    PayRequestVo payRequest = com.alibaba.fastjson.JSON.parseObject(data,PayRequestVo.class);
                    if(StringUtils.isEmpty(payRequest.getOrderId()) || StringUtils.isEmpty(payRequest.getName())|| StringUtils.isEmpty(payRequest.getPkStoreId()))
                        throw new ServiceException("parameter.error");
                    ThirdPayVo vo = shopsService.getPayInfo(dto.getMid(),payRequest.getRateList());
                    List<WealthType> list = vo.getTypeList();
                    JSONArray typeJsonArray = new JSONArray();
                    if( null != list && list.size() > 0 ){
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject bean = new JSONObject(); 
                            WealthType type = list.get(i);
                            bean.put("id", type.getId());
                            bean.put("name", type.getName());
                            bean.put("domainUrl", type.getDomainUrl());
                            bean.put("isDefault", type.getIsDefault() ? "1" : "0");
                            bean.put("balance", type.getBalance());
                            bean.put("cost", type.getCost());
                            typeJsonArray.add(i, bean);
                        }
                    } 
                    result.put("isHasPwd", vo.getIsHasPwd());
                    result.put("name", payRequest.getName());
                    result.put("thirdOrderId", payRequest.getOrderId());
                    result.put("sn", payRequest.getOrderId());
                    result.put("shopsId", payRequest.getPkStoreId());
                    result.put("wealthTypeList", typeJsonArray);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject,null,dto.getLang());
                }else{
                    error(jsonObject, "third.pay.error",dto.getLang());
                }
            }
        });
    }
    
	/**
	 * 创建订单[app请求,回调第三方接口]
	 * @param body
	 * @param request
	 * @param response
	 */
    @RequestMapping( value = "pay/order/submit", method = RequestMethod.POST )
    public void submit(final @RequestBody String body,HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,ThirdDto.class,log,new DoYourBuniness<ThirdDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ThirdDto dto) {
                if(!isLogin(body)){
                    error(jsonObject, "login.error",dto.getLang());
                }
                JSONObject result = new JSONObject();
                ThirdPayResultVo vo = shopsService.createOrder(dto.getMid(),dto.getWealthTypeId(),dto.getPayPassword(),dto.getThirdOrderId(),dto.getShopsId(),
                        dto.getCash(),dto.getName(),dto.getSn());
                if(null != vo){
                    if(vo.getCode() == 200){
                        result.put("guaranteeId", vo.getGuaranteeId());
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
	
	/**
	 * 店铺订单退货
	 * @param body
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping( value = "pay/order/back", method = RequestMethod.POST )
    @ResponseBody
    public String back(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(); 
        try {
            com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSON.parseObject(body);
            com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
            if(ApiUtil.check(THIRD_KEY, data)){
                String  thirdOrderId = data.getString("orderId");
                Object recordId = shopsService.backOrder(thirdOrderId);
                if(null != recordId){
                    result.put("recordId", recordId);
                    jsonObject.put(KEY_STATUS, KEY_SUCCESS);
                    jsonObject.put(KEY_RESULT, result);
                }else{
                    jsonObject.put(KEY_STATUS, KEY_ERROR);
                }
            }else{
                jsonObject.put(KEY_STATUS, LOGIN);
            }
            ApiUtil.encode(THIRD_KEY, jsonObject);
        } catch (Exception ex) {
            jsonObject.put(KEY_STATUS, KEY_ERROR);
            jsonObject.put(KEY_MESSAGE, ex.getMessage());
            ApiUtil.encode(THIRD_KEY, jsonObject);
        }
        return jsonObject.toString();
    }
    
    /**
     * 店铺订单确认
     * @param body
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "pay/order/confirm", method = RequestMethod.POST )
    @ResponseBody
    public String confirm(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(); 
        try {
            com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSON.parseObject(body);
            com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
            if(ApiUtil.check(THIRD_KEY, data)){
                String  thirdOrderId = data.getString("orderId");
                Object recordId = shopsService.confirmOrder(thirdOrderId);
                if(null != recordId){
                    result.put("recordId", recordId);
                    jsonObject.put(KEY_STATUS, KEY_SUCCESS);
                    jsonObject.put(KEY_RESULT, result);
                }else{
                    jsonObject.put(KEY_STATUS, KEY_ERROR);
                }
            }else{
                jsonObject.put(KEY_STATUS, LOGIN);
            }
            ApiUtil.encode(THIRD_KEY, jsonObject);
        } catch (Exception ex) {
            jsonObject.put(KEY_STATUS, KEY_ERROR);
            jsonObject.put(KEY_MESSAGE, ex.getMessage());
            ApiUtil.encode(THIRD_KEY, jsonObject);
        }
        return jsonObject.toString();
    }
    
	//验证是否登陆
	private boolean isLogin(String data){
        try {
            if(StringUtils.isEmpty(data))
                return false;
            AuthDto jsonObject = com.alibaba.fastjson.JSONObject.parseObject(data,AuthDto.class);
            String mid = jsonObject.getMid();
            //用户请求数据不会更新D
            String mchKey = service.getToken(mid, new Date(), 7);
            return ApiUtil.check(mchKey, data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}