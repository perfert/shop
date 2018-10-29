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
import com.mas.core.service.ServiceException;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Group;
import com.mas.user.service.chat.GroupService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.chat.GroupDto;

/**
 * 群组ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberGroupCtr.URI_PREFIX)
public class MemberGroupCtr extends JsonCtr<GroupDto>{
    
    public static final String URI_PREFIX = JsonCtr.prefix + "/group";
    
    @Autowired private GroupService groupService;

    
    /**
     * 获取组ID
     * @param body[mid:会员ID]
     * @param request
     * @param response
     */
    @RequestMapping( value = "list", method = RequestMethod.POST )
    public void list(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto)  {
                List<Group> list = groupService.getListByUserId(dto.getMid());
                JSONArray result = new JSONArray();
                if( null != list && list.size() > 0 ){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject bean = new JSONObject(); 
                        Group group = list.get(i);
                        Object owner = group.getOwner();
                        if(dto.getMid().equalsIgnoreCase(owner.toString()))
                            bean.put("role", 0);//'0: 创建者, 1: 普通成员'
                        else
                            bean.put("role", 1);
                        JSONObject groupJson = new JSONObject(); 
                        groupJson.put("id",group.getId());     
                        groupJson.put("name",group.getName());
                        groupJson.put("portraitUri",HOST +  group.getAvatar());
                        groupJson.put("creatorId",owner.toString());
                        groupJson.put("memberCount",groupService.count(group.getId()));
                        bean.put("group",groupJson);
                        result.add(i, bean);
                    }
                } 
                jsonObject.put( KEY_RESULT, result );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 新建组
     * 
     * @param   groupName   群组的名称
     * @param   owner    管理员账号
     * @param   members 群组成员(会员名#会员名)
     * @param   desc 
     * @param   isPublic    是否是公开群
     * 
     */
    @RequestMapping( value = "create", method = RequestMethod.POST )
    public void create(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto)  {
                Group data = groupService.create(dto.getName(),dto.getMid(),dto.getMemberIds());
                if(null != data ){
                    JSONObject result = new JSONObject();
                    result.put("id", data.getId());
                    result.put("portraitUri",HOST +  data.getAvatar());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
    
    public static void main(String[] args){
        JSONArray j = new JSONArray();
        for(int i =0;i<2;i++){
            j.add(i, "5");
        }
        JSONObject jsonObject = new JSONObject(); 
        jsonObject.put("memberIds", j);
        jsonObject.put("mid", "2");
        System.out.println(jsonObject.toString());
        //String body = "{'memberIds':'[\'s\',\'s\']','mid':'2','lang':'cn','name':'ff'}";
        GroupDto dto = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(), GroupDto.class);
        System.out.println(dto.getMid());
    }
    
    
    /**
     * 修改组名称 [mid:会员ID,groupId:第三方组ID,name:群名称]
     * @param body
     * @param request
     * @param response
     */
    @RequestMapping( value = "name", method = RequestMethod.POST )
    public void name(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto) {
                Object data= groupService.modify(dto.getMid(),dto.getGroupId(),dto.getName());
                if( null != data ){
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
    
    /**
     * 根据第三方推方组ID，获取所有会员数据对
     * 
     * @param groupId 第三方组ID。
     * 
     */
    @RequestMapping( value = "members" )
    public void members(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto)  {
                Group group = groupService.get(dto.getGroupId());
                List<Member> list = groupService.getMembersByGroupId(dto.getGroupId());
                JSONArray array = new JSONArray();
                if(null != list && list.size() > 0){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject result = new JSONObject();
                        result.put("displayName", "");//会员个人改的群别名
                        result.put("createdAt", group.getCreateDate());
                        Member member = list.get(i);
                        JSONObject mJson = new JSONObject();
                        mJson.put("id", member.getId());
                        if(StringUtils.isEmpty(member.getAvatar()))
                            member.setAvatar(LOGO);
                        mJson.put("portraitUri", HOST +  member.getAvatar());
                        mJson.put("nickname", member.getNickName());
                        result.put("user", mJson);
                        if(member.getId().toString().equals(group.getOwner().toString()))
                            result.put("role", 0);//'0: 创建者, 1: 普通成员'
                        else
                            result.put("role", 1);
                        array.add(i,result);
                    }
                }
                jsonObject.put( KEY_RESULT, array );
                success(jsonObject,null,dto.getLang());
            }
        });
    }
   
    /**
     * 增加组成员
     */
    @RequestMapping( value = "addmembers", method = RequestMethod.POST )
    public void addMembers(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto){
                Group data = groupService.addMember(dto.getMid(),dto.getGroupId(),dto.getMemberIds());
                if( null != data ){
                    JSONObject result = new JSONObject();
                    result.put("id", data.getId());
                    result.put("portraitUri",HOST +  data.getAvatar());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
    }
    
    /**
     * 删除组成员
     */
    @RequestMapping( value = "deletemembers", method = RequestMethod.POST )
    public void deleteMembers(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto) {
                Group data= groupService.deleteMember(dto.getMid(),dto.getGroupId(),dto.getMemberIds());
                if( null != data ){
                    JSONObject result = new JSONObject();
                    result.put("id", data.getId());
                    result.put("portraitUri",HOST +  data.getAvatar());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());;
                } else {
                    error(jsonObject,null,dto.getLang());
                }
            }
        });
        
    }
    
    /**
     * 获取组信息
     */
    @RequestMapping( value = "info", method = RequestMethod.POST )
    public void info(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto) {
                Group group = groupService.get(dto.getGroupId());
                if( null != group ){
                    JSONObject result = new JSONObject();
                    result.put("id", group.getId());
                    result.put("name", group.getName());
                    result.put("portraitUri",HOST + group.getAvatar());
                    result.put("creatorId", group.getOwner());
                    result.put("memberCount", group.getNowCount());
                    jsonObject.put( KEY_RESULT, result );
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,"group.error.noexist",dto.getLang());
                }
            }
        });

    }
    
    /**
     * 删除群组
     */
    @RequestMapping( value = "dismiss", method = RequestMethod.POST )
    public void delete(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto){
                Group group = groupService.get(dto.getGroupId());
                if( null != group ){
                    if(!group.getOwner().toString().equalsIgnoreCase(dto.getMid()))
                        throw new ServiceException("group.error.security");
                    boolean data = groupService.delete(dto.getMid(),dto.getGroupId());
                    if(data)
                        success(jsonObject,null,dto.getLang());
                    else
                        error(jsonObject,"delete.error",dto.getLang());
                } else {
                    error(jsonObject,"group.error.noexist",dto.getLang());
                }
            }
        });
    }
    
    /**
     *  退群
     */
    @RequestMapping( value = "quit", method = RequestMethod.POST )
    public void quit(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,GroupDto.class,log,new DoYourBuniness<GroupDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject, GroupDto dto) {
                Group group = groupService.get(dto.getGroupId());
                if( null != group ){
                    if(!group.getOwner().toString().equalsIgnoreCase(dto.getMid())){
                        boolean data= groupService.deleteMember(dto.getMid(),dto.getGroupId());
                        if( data )
                            success(jsonObject,null,dto.getLang());
                         else 
                            error(jsonObject,null,dto.getLang());
                    }else{
                        //群主直接删除群
                        boolean data = groupService.delete(dto.getGroupId());
                        if(data)
                            success(jsonObject,null,dto.getLang());
                        else
                            error(jsonObject,"delete.error",dto.getLang());
                    }
                } else {
                    error(jsonObject,"group.error.noexist",dto.getLang());
                }
            }
        });
    }
    
    private static final Logger log = LogManager.getLogger( MemberGroupCtr.class );
}