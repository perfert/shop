/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.app;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.service.app.AdPositionService;
import com.mas.web.member.controller.JsonCtr;

/**
 * 文章Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberAdCtr.URI_PREFIX)
public class MemberAdCtr extends JsonCtr{
	
    public static final String URI_PREFIX = "app/ad";
    @Autowired private AdPositionService  adPositionService;
    
    private static final Logger log = LogManager.getLogger( MemberAdCtr.class );
    
    /**
     * @param   sign   广告位标识
     */
    @RequestMapping( value = "list" )
    public void list(String sign,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        JSONArray array = new JSONArray();
        try {
            List<Ad> list =  adPositionService.getAdListBySign(sign);  
            if( null != list && list.size() > 0 ){
                for (int i = 0; i < list.size(); i++) {
                    Ad ad = list.get(i);
                    JSONObject mJson = new JSONObject();
                    mJson.put("title", ad.getTitle());
                    mJson.put("type", ad.getType());
                    if(Ad.Type.TEXT.value() != ad.getType())
                        mJson.put("path", ad.getFilePath());
                    else
                        mJson.put("content", ad.getContent());
                    mJson.put("url", ad.getUrl());
                    array.put(i,mJson);
                }
                jsonObject.put("data", array);
                jsonObject.put( "statusCode", 200 );
            }else{
                jsonObject.put("msg", "数据不存在");
                jsonObject.put( "statusCode", 0 );
            }
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject);
        }
        writeJson(jsonObject, response);
    }
    
}