/**    
 * 文件名：IGroupService.java    
 *    
 * 版本信息：    
 * 日期：2017-10-7    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package io.rong.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rong.RongCloud;
import io.rong.models.CodeSuccessResult;
import io.rong.models.GroupInfo;
import io.rong.models.GroupUserQueryResult;
import io.rong.models.ListGagGroupUserResult;
import io.rong.service.GroupService;

/**    
 *     
 * 项目名称：server-sdk-java-master    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class IGroupService implements GroupService{
    
    RongCloud rongCloud = RongCloud.getInstance();
    
    public CodeSuccessResult create(String[] userId, String groupId, String groupName) throws Exception{
        CodeSuccessResult groupCreateResult = rongCloud.group.create(userId, groupId, groupName);
        System.out.println("create:  " + groupCreateResult.toString());
        return groupCreateResult;
    }
    
    public CodeSuccessResult sync(String userId, GroupInfo[] groupInfo) throws Exception{
        //GroupInfo[] groupSyncGroupInfo = {new GroupInfo("groupId1","groupName1" ), new GroupInfo("groupId2","groupName2" ), new GroupInfo("groupId3","groupName3" )};
        CodeSuccessResult groupSyncResult = rongCloud.group.sync(userId, groupInfo);
        System.out.println("sync:  " + groupSyncResult.toString());
        return groupSyncResult;
    }
    
    public CodeSuccessResult refresh(String groupId, String groupName) throws Exception{
        CodeSuccessResult groupRefreshResult = rongCloud.group.refresh(groupId, groupName);
        System.out.println("refresh:  " + groupRefreshResult.toString());
        return groupRefreshResult;
    }
    
    public CodeSuccessResult join(String[] userId, String groupId, String groupName) throws Exception{
        CodeSuccessResult groupJoinResult = rongCloud.group.join(userId, groupId, groupName);
        System.out.println("join:  " + groupJoinResult.toString());
        return groupJoinResult;
    }
    
    public GroupUserQueryResult queryUser(String groupId) throws Exception{
        GroupUserQueryResult groupQueryUserResult = rongCloud.group.queryUser(groupId);
        System.out.println("queryUser:  " + groupQueryUserResult.toString());
        return groupQueryUserResult;
    }
    
    public CodeSuccessResult quit(String[] userId, String groupId) throws Exception{
        CodeSuccessResult groupQuitResult = rongCloud.group.quit(userId,groupId);
        System.out.println("quit:  " + groupQuitResult.toString());
        return groupQuitResult;
    }
    
    public CodeSuccessResult addGagUser(String userId, String groupId, String minute) throws Exception{
        CodeSuccessResult groupAddGagUserResult = rongCloud.group.addGagUser(userId, groupId,minute);
        System.out.println("addGagUser:  " + groupAddGagUserResult.toString());
        return groupAddGagUserResult;
    }
    
    public ListGagGroupUserResult lisGagUser(String groupId) throws Exception{
        ListGagGroupUserResult groupLisGagUserResult = rongCloud.group.lisGagUser(groupId);
        System.out.println("lisGagUser:  " + groupLisGagUserResult.toString());
        return groupLisGagUserResult;
    }
    
    // 移除禁言群成员方法 
    public CodeSuccessResult rollBackGagUser(String[] userId, String groupId) throws Exception{
        CodeSuccessResult groupRollBackGagUserResult = rongCloud.group.rollBackGagUser(userId, groupId);
        System.out.println("rollBackGagUser:  " + groupRollBackGagUserResult.toString());
        return groupRollBackGagUserResult;
    }
    
    // 解散群组方法。（将该群解散，所有用户都无法再接收该群的消息。） 
    public CodeSuccessResult dismiss(String userId, String groupId) throws Exception{
        CodeSuccessResult groupDismissResult = rongCloud.group.dismiss(userId, groupId);
        System.out.println("dismiss:  " + groupDismissResult.toString());
        return groupDismissResult;
    }
    
}
