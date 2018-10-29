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
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.FriendsApply;
import com.mas.user.service.chat.FriendsApplyService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.ApplyDto;

/**
 * 好友申请
 * 列表只展示好友申请
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberFriendsApplyCtr.URI_PREFIX)
public class MemberFriendsApplyCtr extends JsonCtr<ApplyDto>{
	
    public static final String URI_PREFIX = JsonCtr.prefix + "/apply";
    
    @Autowired 
    private FriendsApplyService applyService;
    
    //获取用户申请列表
    @RequestMapping( value = "list" )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ApplyDto.class,log,new DoYourBuniness<ApplyDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, ApplyDto dto) {
                List<FriendsApply> list = applyService.getListByUserId(dto.getMid());
                JSONArray result = new JSONArray();
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject bean = new JSONObject(); 
                        FriendsApply apply = list.get(i);
                        //bean.put("displayName",apply.getd()); 
                        bean.put("message",apply.getMsg());     
                        bean.put("status", apply.getType());
                        bean.put("applyId",apply.getApplyId());   
                        if(StringUtils.isEmpty(apply.getApplyAvatar()))
                            apply.setApplyAvatar(LOGO);
                        bean.put("applyAvatar",HOST +  apply.getApplyAvatar());   
                        bean.put("applyNickName",apply.getApplyNickName());  
                        bean.put("updatedAt", DateTimeUtil.format(DateTimeUtil.YYYY_MM_DD_HH_MM_SS,apply.getModifyDate()) );
                        result.add(i, bean);
                    }  
                } 
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 创建好友申请[@TODO需要发推送]
     * @param   mid    申请账号
     * @param   friendId  好友账号
     * @param   message   原因
     */
    @RequestMapping( value = "send" )
    public void send(@RequestBody String body,HttpServletRequest request, HttpServletResponse response) {
        doInCallBack(response,body,ApplyDto.class,log,new DoYourBuniness<ApplyDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, ApplyDto dto) {
                Object object = applyService.create(dto.getMid(),dto.getFriendId(),dto.getMessage());
                if( null != object){
                    success(jsonObject,null,dto.getLang());
                }else
                    error(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 申请拒绝
     * @param   apply    申请账号
     * @param   receive  好友账号
     */
	@RequestMapping( value = "refuse", method = RequestMethod.POST )
	public void refuse(@RequestBody String body,String receive,HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,ApplyDto.class,log,new DoYourBuniness<ApplyDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, ApplyDto dto) {
                Object object = applyService.refuse(dto.getMid(),dto.getFriendId());
                if( null != object){
                    success(jsonObject,null,dto.getLang());
                }else
                    error(jsonObject,null,dto.getLang());
            }
        });
	}
	
	 /**
     * 申请同意
     * @param   user    申请账号
     * @param   friend  好友账号
     */
	@RequestMapping( value = "agree", method = RequestMethod.POST )
    public void agree(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,ApplyDto.class,log,new DoYourBuniness<ApplyDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, ApplyDto dto) {
                Member friends = friendsApplyService.accept(dto.getMid(),dto.getFriendId());
                if( null != friends){
                    success(jsonObject,"apply.agree.success",dto.getLang());
                }else
                    error(jsonObject,"apply.agree.fail",dto.getLang());
            }
        });
    }
	
	@Autowired private FriendsApplyService friendsApplyService;
	
	private static final Logger log = LogManager.getLogger( MemberFriendsApplyCtr.class );
}