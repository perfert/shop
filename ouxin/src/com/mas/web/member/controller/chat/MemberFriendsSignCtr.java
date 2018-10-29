/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.chat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Sign;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.service.chat.SignMemberService;
import com.mas.user.service.chat.SignService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.FriendsSignDto;

/**
 * 好友标签ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberFriendsSignCtr.URI_PREFIX)
public class MemberFriendsSignCtr extends JsonCtr<FriendsSignDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/sign";
    
    @Autowired 
    private SignService service;
    @Autowired private SignMemberService signMemberService;
   
    @RequestMapping( value = "list" )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response,body,FriendsSignDto.class,log,new DoYourBuniness<FriendsSignDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsSignDto dto)  {
                JSONArray array = new JSONArray();
                List<Sign> list = service.getSignListByUsername(dto.getMid());
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        Sign sign = list.get(i);
                        JSONObject mJson = new JSONObject();
                        mJson.put("id", sign.getId());
                        mJson.put("name", sign.getName());
                        mJson.put("detail", sign.getDetailName()); 
                        mJson.put("num", sign.getNum()); 
                        
                        List<SignMember> gList = signMemberService.getSignMemberBySignId(sign.getId());
                        JSONArray friendsArray = new JSONArray();
                        if(null != gList && gList.size() > 0){
                            for (int j = 0; j < gList.size(); j++) {
                                JSONObject signMemberJson = new JSONObject();
                                SignMember signMember = gList.get(j);
                                signMemberJson.put(Member.JSON_KEY_ID, signMember.getMemberId());
                                signMemberJson.put(Member.JSON_KEY_SPLIT, Member.CODE_ACCOUNT_SPLIT);
                                signMemberJson.put(Member.JSON_KEY_USERNAME, signMember.getUsername());
                                signMemberJson.put(Member.JSON_KEY_NICK, signMember.getNickName());
                                signMemberJson.put("alias", signMember.getAlias());
                                signMemberJson.put(Member.JSON_KEY_AVATAR, signMember.getAvatar());
                                friendsArray.add(j,signMemberJson);
                            }
                        }
                        mJson.put("friends", friendsArray);
                        array.add(i,mJson);
                    }
                } 
                jsonObject.put( KEY_RESULT, array );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
   
    
    @RequestMapping( value = "add", method = RequestMethod.POST )
    public void save(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,FriendsSignDto.class,log,new DoYourBuniness<FriendsSignDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsSignDto dto)  {
                Object data = null;
                String[] listMember = null;
                if (null != dto.getMembers() && dto.getMembers().size() > 0) {
                    List<String> list= dto.getMembers();
                    listMember = new String[list.size()];
                    list.toArray(listMember);
                }
                data = service.save(dto.getMid(), dto.getName(), listMember);
                if(null != data ){
                    JSONObject result = new JSONObject();
                    result.put("id", data.toString());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });

    }
    
    @RequestMapping( value = "update", method = RequestMethod.POST )
    public void update(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,FriendsSignDto.class,log,new DoYourBuniness<FriendsSignDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsSignDto dto)  {
                Object data = null;
                String[] listMember = null;
                if (null != dto.getMembers() && dto.getMembers().size() > 0) {
                    List<String> list= dto.getMembers();
                    listMember = new String[list.size()];
                    list.toArray(listMember);
                }
                data = service.update(dto.getId(), dto.getMid(), dto.getName(), listMember);
                if(null != data ){
                    JSONObject result = new JSONObject();
                    result.put("id", data.toString());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
    
    /*//如果你单纯接收一个list参数，list虽然有get和set方法，但是没有名字呀，只能根据数组下标来判断参数位置
    @RequestMapping( value = "update", method = RequestMethod.POST )
    public void update(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject(); 
        try {
            FriendsSignDto dto = (FriendsSignDto) parseObject(body, FriendsSignDto.class);
            Object data= null;
            List<Sign> signs = new ArrayList<Sign>();
            for(String id : dto.getSigns()){
                if(StringUtils.isNotEmpty(id)){
                    signs.add(new Sign(id));
                }
            }
            signs.add(new Sign(dto.getId()));
            data = service.update(dto.getMid(),dto.getFriendId(),signs);
            if (null != data) 
                success(jsonObject,null,dto.getLang());
            else
                error(jsonObject,null,dto.getLang());
        } catch( Exception ex ){
            handlerException(ex, log, jsonObject);
        }
        writeJson(jsonObject, response);
    }*/
   
    /**
     * 删除
     */
    @RequestMapping( value = "delete", method = RequestMethod.POST )
    public void delete(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,FriendsSignDto.class,log,new DoYourBuniness<FriendsSignDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, FriendsSignDto dto)  {
                if( service.delete(dto.getId(),dto.getMid()) )
                    success(jsonObject,null,dto.getLang());
                else 
                    error(jsonObject,null,dto.getLang());
            }
        });
    }
    
	private static final Logger log = LogManager.getLogger( MemberFriendsSignCtr.class );
}