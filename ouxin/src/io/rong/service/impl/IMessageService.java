/**    
 * 文件名：IMessageService.java    
 *    
 * 版本信息：    
 * 日期：2017-10-7    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package io.rong.service.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.messages.CmdMsgMessage;
import io.rong.messages.GrpNtfMessage;
import io.rong.messages.TxtMessage;
import io.rong.models.CodeSuccessResult;
import io.rong.models.MsgObj;
import io.rong.models.Notification;
import io.rong.models.PushMessage;
import io.rong.models.TagObj;
import io.rong.service.MessageService;

/**    
 *     
 * 项目名称：server-sdk-java-master    
 * 类名称：    消息Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class IMessageService implements MessageService{

    RongCloud rongCloud = RongCloud.getInstance();
    
   
    public CodeSuccessResult publishSystem(String fromUserId, String[] toUserId, BaseMessage message, String pushContent, String pushData, Integer isPersisted, Integer isCounted) throws Exception{
        TxtMessage messagePublishSystemTxtMessage = new TxtMessage(pushContent, pushData);
        CodeSuccessResult messagePublishSystemResult = rongCloud.message.PublishSystem(fromUserId, toUserId, messagePublishSystemTxtMessage, pushContent, pushData, isPersisted, isCounted);
        System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());
        return messagePublishSystemResult;
    }
    
    // 广播消息方法（fromuserid 和 message为null即为不落地的push） 
    public CodeSuccessResult broadcastPush(String[] platform, String fromuserid, TagObj audience, MsgObj message, Notification notification) throws Exception{
        PushMessage broadcastPushPushMessage  =  new PushMessage(platform, fromuserid, audience, message, notification);
        CodeSuccessResult pushBroadcastPushResult = rongCloud.push.broadcastPush(broadcastPushPushMessage);
        System.out.println("broadcastPush:  " + pushBroadcastPushResult.toString());
        return pushBroadcastPushResult;
    }
    
    
    public static void main(String[] args) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance();
        //{"operatorUserId":"4324","operation":"Rename","data":"{\"operatorNickname\":\"李天\",\"targetGroupName\":\"群名\"}","message":"修改本群名为本地生活","extra":""}
        //{"operatorUserId":"4324","operation":"Create","data":"{\"operatorNickname\":\"李天\",\"targetGroupName\":\"群名\"}","message":"创建群组","extra":""}
        String groupId = "60";
        String userId = "71";
        IGroupService groupService = new IGroupService();
        //CodeSuccessResult result = groupService.create(new String[]{userId}, groupId, "hello");
        

        String operation = "Create";
        String message = "创建群组";
        JSONObject json = new JSONObject();
        json.put("operatorNickname", "dongge");
        json.put("targetGroupName", "测试创建");
        String data = json.toString();
        String extra = "";
        GrpNtfMessage grpNtf = new GrpNtfMessage(userId,operation,data,message,extra);
        
        String[] messagePublishGroupToGroupId = {groupId};
        CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(userId, messagePublishGroupToGroupId, grpNtf, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 1);
        System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
        

       /* String operation = "Request";
        String extra = "";
        String sourceUserId = "73";
        String targetUserId = "70";
        String message = "hello.加我";
        ContactNtfMessage grpNtf = new ContactNtfMessage(operation,extra,sourceUserId,targetUserId,message);*/
         
       /* String name = "AtPerson";
        JSONObject json = new JSONObject();
        json.put("sourceId", "70");
        json.put("targetGroupName", "测试创建");
        CmdMsgMessage cmd = new CmdMsgMessage(name,json.toString());
        
        String[] messagePublishPrivateToUserId = {"70"};
        
        CodeSuccessResult messagePublishPrivateResult = rongCloud.message.publishPrivate("73", messagePublishPrivateToUserId, cmd, "thisisapush", "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
        System.out.println("publishPrivate:  " + messagePublishPrivateResult.toString());
        */
       /* String[] messagePublishPrivateToUserId = {"70"};
        VoiceMessage messagePublishPrivateVoiceMessage = new VoiceMessage("hello", "helloExtra", 20L);
        
        CodeSuccessResult messagePublishPrivateResult = rongCloud.message.publishPrivate("71", messagePublishPrivateToUserId, messagePublishPrivateVoiceMessage, "thisisapush", "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
        System.out.println("publishPrivate:  " + messagePublishPrivateResult.toString());*/
        
       /* String[] messagePublishSystemToUserId = {"userId2","userid3","userId4"};
        TxtMessage messagePublishSystemTxtMessage = new TxtMessage("hello", "helloExtra");
        CodeSuccessResult messagePublishSystemResult = rongCloud.message.PublishSystem("userId1", messagePublishSystemToUserId, messagePublishSystemTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 0, 0);
        System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());*/
        
       /* String[] messagePublishGroupToGroupId = {"31"};
        CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup("userId", messagePublishGroupToGroupId, grpNtf, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
        System.out.println("publishGroup:  " + messagePublishGroupResult.toString());*/
        
    }

    @Override
    public CodeSuccessResult publishGrpNtfMessage(String[] groupIds,String operatorUserId, String operation, String data, String message,String extra) throws Exception {
        GrpNtfMessage grpNtf = new GrpNtfMessage(operatorUserId,operation,data,message,extra);
        CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(operatorUserId, groupIds, grpNtf, null, "{\"pushData\":\"hello\"}", 1, 1, 1);
        return messagePublishGroupResult;
    }

    @Override
    public CodeSuccessResult publishCmdMsgMessageFriend(String sendId, String targetId,String type,String data) throws Exception{
        CmdMsgMessage cmd = new CmdMsgMessage(type,data);
        String[] messagePublishPrivateToUserId = {targetId};
        
        CodeSuccessResult messagePublishPrivateResult = rongCloud.message.publishPrivate(sendId, messagePublishPrivateToUserId, cmd, "thisisapush", "{\"pushData\":\"hello\"}", "4", 0, 0, 1, 0);
        return messagePublishPrivateResult;
    }
}
