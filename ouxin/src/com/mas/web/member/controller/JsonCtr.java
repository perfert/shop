/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.mas.common.orm.util.Page;
import com.mas.core.service.ServiceException;
import com.mas.security.util.verify.ApiUtil;
import com.mas.user.service.MemberService;
import com.mas.web.member.controller.dto.BaseDto;
import com.mas.web.springmvc.controller.BaseCtr;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * Abstract Json controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
public abstract class JsonCtr<T extends BaseDto> extends BaseCtr{
    
    public static final String prefix = "api";
    public static final String HOST = "http://54.153.39.25/";//http://ouxinchat.com/;  http://192.168.1.168/
    public static final String LOGO = "oxin/default_useravatar_new.png";
    public static final String LANG = "zh";
    
    protected Integer size;  //每页记录数
    protected Integer no;    //当前页码
    protected static final int DEFAULT_SIZE = 10;
    
    public static final String KEY_STATUS = "code";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_RESULT = "data";
    public static final String DEFAULT_LANG = "cn";
    public static final String FORM_DTTA_KEY = " data";
    public static final int KEY_SUCCESS = 200;
    public static final int KEY_ERROR = 0;
    public static final int LOGIN = 403;
    //第三方商城回调
    public static final String THIRD_KEY = "4f40192c2bc17ecef16ee4eff8971a2014ba7560";
    public static final String THIRD_PAY_ORDER = "https://mall.ouxinchat.com/m/api/orderPayment/orderPaymentNotice";
    //中英文
    public static final String LANGUAGE_CN = "zh";
    public static final String LANGUAGE_EN = "en";
    
    @Autowired
    protected MemberService memberService;
    
   /**
    * 获取国家语言,linux直接用zh转不了
    * @param lang
    * @return
    */
    protected Locale getLocal(String lang){
        Locale local = null;
        if (lang.equalsIgnoreCase(LANGUAGE_CN)) {
            local = Locale.CHINA;
        } else {
            local = Locale.ENGLISH;
        }
        return local;
    }
    
    /**
     * 请求参数转Class
     * @param body
     * @param clazz
     * @return
     */
    protected BaseDto parseObject(String body,Class<? extends BaseDto> clazz){
        return com.alibaba.fastjson.JSONObject.parseObject(body, clazz);
    }
    
    /**
     * 请求参数转Class
     * @param body
     * @param clazz
     * @return
     */
    protected BaseDto parseObjectVerify(String body,Class<? extends BaseDto> clazz){
        //加解密
        return com.alibaba.fastjson.JSONObject.parseObject(body, clazz);
    }
    
    /**
     * 业务回调
     * @param response
     * @param body
     * @param clazz
     * @param log
     * @param business
     */
    public void doInCallBack(HttpServletResponse response,String body,Class<T> clazz,Logger log,DoYourBuniness<T> business){
        String lang = LANG;
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject(); 
        String mid = null;
        try {
            @SuppressWarnings("unchecked")
            T dto = (T) parseObject(body, clazz);
            lang = dto.getLang();
            mid = dto.getMid();
            if(!lang.contains(LANGUAGE_CN) && !lang.contains(LANGUAGE_EN))
                dto.setLang(DEFAULT_LANG);
            business.handleBusiness(jsonObject,dto);
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject , lang);
        }   
        //未登陆则不加密
        if(StringUtils.isNotEmpty(mid))
            ApiUtil.encode(memberService.getToken(mid, new Date(), 7), jsonObject);
       /* if(!ApiUtil.check(key, jsonObject.toJSONString())){
            System.out.println("111=");
            System.out.println(jsonObject.toJSONString());
        }*/
        writeJson(jsonObject, response);
    }

    public interface DoYourBuniness<T extends BaseDto>{
        
        /**
         * 回调处理
         * @param jsonObject 返回JSON
         * @param body       请求参数
         * @throws Exception
         */
        void  handleBusiness(com.alibaba.fastjson.JSONObject jsonObject,T dto) throws Exception;
    }
    
    /**
     * 返回操作成功
     * @param jsonObject
     * @param i18nMessage 语言KEY
     * @param lang        语种
     * @return
     * @throws JSONException
     */
    protected com.alibaba.fastjson.JSONObject success(com.alibaba.fastjson.JSONObject jsonObject,String i18nMessage,String lang){
        Locale local = new Locale(lang);//z
        String message = null;
        if(StringUtils.isNotBlank(i18nMessage))
            message = SpringUtils.getApplicationContext().getMessage(i18nMessage, null, local);
        else
            message = SpringUtils.getApplicationContext().getMessage("result.success", null, local);
        jsonObject.put( KEY_STATUS, KEY_SUCCESS );
        jsonObject.put( KEY_MESSAGE,message);
        return jsonObject;
    }
    
    /**
     * 返回操作失败
     * @param jsonObject
     * @param i18nMessage 语言KEY
     * @param lang        语种
     * @return
     * @throws JSONException
     */
    protected com.alibaba.fastjson.JSONObject error(com.alibaba.fastjson.JSONObject jsonObject,String i18nMessage,String lang){
        Locale local = new Locale(lang);
        String message = null;
        if(StringUtils.isNotBlank(i18nMessage))
            message = SpringUtils.getApplicationContext().getMessage(i18nMessage, null, local);
        else
            message = SpringUtils.getApplicationContext().getMessage("result.error", null, local);
        jsonObject.put( KEY_STATUS, KEY_ERROR );
        jsonObject.put( KEY_MESSAGE,message);
        return jsonObject;
    }
    
    protected void writeJsonPage(Page page,JSONArray data,HttpServletResponse response) throws JSONException, IOException  {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",page.getTotalCount());      //总数
        jsonObject.put("size",page.getPageSize());      //总数
        jsonObject.put("no",page.getPageNo());      //总数
        jsonObject.put("result", data);
        writeJson(jsonObject,response);
    }
    
    protected void writeJson(Object object,HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            out.write(object.toString());
            out.flush();
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
    
    protected void handlerException(Exception ex, Logger log, JSONObject jsonObject){
        try {
            ex.printStackTrace();
            jsonObject.put(KEY_STATUS, 0);
            if( ! (ex instanceof ServiceException) ) {
                log.error( ex.getMessage() );
                jsonObject.put(KEY_MESSAGE, "服务器出错,请联系技术人员");
            }else
                jsonObject.put(KEY_MESSAGE, ex.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
   
    protected void handlerException(Exception ex, Logger log, com.alibaba.fastjson.JSONObject jsonObject,String lang){
        Locale local = new Locale(lang);
        if(lang.equalsIgnoreCase("zh")){
            local = Locale.CHINA;
        }else{
            local = Locale.ENGLISH;
        }
        String message = null;
        if( ! (ex instanceof ServiceException) ) {
            log.error( ex.getMessage() );
            message = SpringUtils.getApplicationContext().getMessage("net.error.web", null, local);
        }else{
            if(StringUtils.isNotBlank(ex.getMessage()))
                message = SpringUtils.getApplicationContext().getMessage(ex.getMessage(), null, local);
            else
                message = SpringUtils.getApplicationContext().getMessage("result.error", null, local);
        }
        jsonObject.put(KEY_STATUS, 0);
        jsonObject.put(KEY_MESSAGE, message);
    }
    
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public Integer getNo() {
        return no;
    }
    public void setNo(Integer no) {
        this.no = no;
    }
}