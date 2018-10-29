/**    
 * 文件名：GroupService.java    
 *    
 * 版本信息：    
 * 日期：2017-10-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package io.rong.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rong.RongCloud;
import io.rong.models.CheckOnlineResult;
import io.rong.models.CodeSuccessResult;
import io.rong.models.QueryBlacklistUserResult;
import io.rong.models.QueryBlockUserResult;
import io.rong.models.TokenResult;
import io.rong.service.UserService;

/**    
 *     
 * 项目名称：server-sdk-java-master    
 * 类名称：    用户Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class IUserService implements UserService{
   
    RongCloud rongCloud = RongCloud.getInstance();
    
    // 获取 Token 方法 
    public TokenResult getToken(String userId,String userName,String portraitUri) throws Exception{
        TokenResult userGetTokenResult = rongCloud.user.getToken(userId, userName, portraitUri);
        return userGetTokenResult;
    }
    
    // 刷新用户信息方法 
    public CodeSuccessResult refresh(String userId,String userName,String portraitUri) throws Exception{
        CodeSuccessResult userRefreshResult = rongCloud.user.refresh(userId, userName, portraitUri);
        System.out.println("refresh:  " + userRefreshResult.toString());
        return userRefreshResult;
    }
    
    // 检查用户在线状态 方法 
    public CheckOnlineResult checkOnline(String userId) throws Exception{
        CheckOnlineResult userCheckOnlineResult = rongCloud.user.checkOnline(userId);
        System.out.println("checkOnline:  " + userCheckOnlineResult.toString());
        return userCheckOnlineResult;
    }
    
    // 封禁用户方法（每秒钟限 100 次） 
    public CodeSuccessResult userBlock(String userId,Integer minute) throws Exception{
        CodeSuccessResult userBlockResult = rongCloud.user.block(userId, minute);
        System.out.println("block:  " + userBlockResult.toString());
        return userBlockResult;
    }
    
    // 解除用户封禁方法（每秒钟限 100 次） 
    public CodeSuccessResult relieveUserBlock(String userId) throws Exception{
        CodeSuccessResult userUnBlockResult = rongCloud.user.unBlock(userId);
        System.out.println("unBlock:  " + userUnBlockResult.toString());
        return userUnBlockResult;
    }
    
    // 获取被封禁用户方法（每秒钟限 100 次） 
    public QueryBlockUserResult userQueryBlock() throws Exception{
        QueryBlockUserResult userQueryBlockResult = rongCloud.user.queryBlock();
        System.out.println("queryBlock:  " + userQueryBlockResult.toString());
        return userQueryBlockResult;
    }
    
    // 添加用户到黑名单方法（每秒钟限 100 次） 
    public CodeSuccessResult userAddBlacklist() throws Exception{
        CodeSuccessResult userAddBlacklistResult = rongCloud.user.addBlacklist("userId1", "userId2");
        System.out.println("addBlacklist:  " + userAddBlacklistResult.toString());
        return userAddBlacklistResult;
    }
    
    // 获取某用户的黑名单列表方法（每秒钟限 100 次） 
    public QueryBlacklistUserResult userQueryBlacklist() throws Exception{
        QueryBlacklistUserResult userQueryBlacklistResult = rongCloud.user.queryBlacklist("userId1");
        System.out.println("queryBlacklist:  " + userQueryBlacklistResult.toString());
        return userQueryBlacklistResult;
    }
    
    // 从黑名单中移除用户方法（每秒钟限 100 次） 
    public CodeSuccessResult removeBlacklist() throws Exception{
        CodeSuccessResult userRemoveBlacklistResult = rongCloud.user.removeBlacklist("userId1", "userId2");
        System.out.println("removeBlacklist:  " + userRemoveBlacklistResult.toString());
        return userRemoveBlacklistResult;
    }
    
    public static void main(String[] args) throws Exception {
        IUserService service = new IUserService();
        TokenResult result = service.getToken("dong", "dongge", "http://www.rongcloud.cn/images/logo.png");
        System.out.println(result);
    }
    
}
