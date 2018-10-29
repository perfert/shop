/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.chat;
import java.util.List;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.common.util.DateTimeUtil;
import com.mas.core.service.ServiceException;
import com.mas.user.domain.entity.Member;
import com.mas.user.service.MemberService;
import com.mas.user.service.chat.FriendsService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.FriendsDto;

/**
 * 好友ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberFriendsCtr.URI_PREFIX)
public class MemberFriendsCtr extends JsonCtr<FriendsDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/friends";
    
    //获取用户所有好友[包括别名]
    @RequestMapping( value = "list" )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,FriendsDto.class,log,new DoYourBuniness<FriendsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsDto dto) {
                JSONArray result = new JSONArray();
                List<Member> list = friendsService.getFriendsListByUserId(dto.getMid());
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject bean = new JSONObject(); 
                        Member member = list.get(i);
                        bean.put("displayName",member.getAlias());   
                        bean.put("status",member.getState());     
                        bean.put("updatedAt",DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,member.getCreateDate()));
                        JSONObject user = new JSONObject(); 
                        user.put("id", member.getId());
                        if(StringUtils.isEmpty(member.getAvatar()))
                            member.setAvatar(LOGO);
                        user.put("portraitUri", HOST + member.getAvatar());
                        user.put("nickname", member.getNickName());
                        bean.put("user",user);
                        result.add(i, bean);
                    }
                } 
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    //好友查找
	@RequestMapping( value = "search", method = RequestMethod.POST )
	public void search(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,FriendsDto.class,log,new DoYourBuniness<FriendsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsDto dto) {
                Member member = service.getByPhoneOrWxid(dto.getPhone());
                if( null != member ){
                    JSONObject result = new JSONObject(); 
                    result.put("id", member.getId());
                    result.put("portraitUri",HOST + member.getAvatar());
                    result.put("nickname", member.getNickName());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                } else {
                    throw new ServiceException("user.error.noexist");
                }
            }
        });
	}
	
	//好友删除
	@RequestMapping( value = "delete", method = RequestMethod.POST )
    public void delete(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,FriendsDto.class,log,new DoYourBuniness<FriendsDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsDto dto){
                boolean success = friendsService.delete(dto.getMid(),dto.getFriendId());
                if( success ){
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
	
	@Autowired private MemberService service;
	@Autowired private FriendsService friendsService;
	
	private static final Logger log = LogManager.getLogger( MemberFriendsCtr.class );
}