/**    
 * 文件名：GroupService.java    
 *    
 * 版本信息：    
 * 日期：2017-10-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package io.rong.service;

import io.rong.models.CheckOnlineResult;
import io.rong.models.CodeSuccessResult;
import io.rong.models.QueryBlacklistUserResult;
import io.rong.models.QueryBlockUserResult;
import io.rong.models.TokenResult;

/**    
 *     
 * 项目名称：server-sdk-java-master    
 * 类名称：    用户Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface UserService {
    
    /**
     * 获取 Token 方法 
     * 
     * @param  userId:用户 Id，最大长度 64 字节.是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户。（必传）
     * @param  name:用户名称，最大长度 128 字节.用来在 Push 推送时显示用户的名称.用户名称，最大长度 128 字节.用来在 Push 推送时显示用户的名称。（必传）
     * @param  portraitUri:用户头像 URI，最大长度 1024 字节.用来在 Push 推送时显示用户的头像。（必传）
     *
     * @return TokenResult
     **/
    public TokenResult getToken(String userId,String userName,String portraitUri) throws Exception;
    
    /**
     * 刷新用户信息方法 
     * 
     * @param  userId:用户 Id，最大长度 64 字节.是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户。（必传）
     * @param  name:用户名称，最大长度 128 字节。用来在 Push 推送时，显示用户的名称，刷新用户名称后 5 分钟内生效。（可选，提供即刷新，不提供忽略）
     * @param  portraitUri:用户头像 URI，最大长度 1024 字节。用来在 Push 推送时显示。（可选，提供即刷新，不提供忽略）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessResult refresh(String userId,String userName,String portraitUri) throws Exception;
    
    /**
     * 检查用户在线状态 方法 
     * 
     * @param  userId:用户 Id，最大长度 64 字节。是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户。（必传）
     *
     * @return CheckOnlineResult
     **/
    public CheckOnlineResult checkOnline(String userId) throws Exception;
    
    /**
     * 封禁用户方法（每秒钟限 100 次） 
     * 
     * @param  userId:用户 Id。（必传）
     * @param  minute:封禁时长,单位为分钟，最大值为43200分钟。（必传）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessResult userBlock(String userId,Integer minute) throws Exception;
    
    /**
     * 解除用户封禁方法（每秒钟限 100 次） 
     * 
     * @param  userId:用户 Id。（必传）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessResult relieveUserBlock(String userId) throws Exception;
    
    /**
     * 获取被封禁用户方法（每秒钟限 100 次） 
     * 
     *
     * @return QueryBlockUserResult
     **/
    public QueryBlockUserResult userQueryBlock() throws Exception;
    
    /**
     * 添加用户到黑名单方法（每秒钟限 100 次） 
     * 
     * @param  userId:用户 Id。（必传）
     * @param  blackUserId:被加到黑名单的用户Id。（必传）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessResult userAddBlacklist() throws Exception;
    
    /**
     * 获取某用户的黑名单列表方法（每秒钟限 100 次） 
     * 
     * @param  userId:用户 Id。（必传）
     *
     * @return QueryBlacklistUserResult
     **/
    public QueryBlacklistUserResult userQueryBlacklist() throws Exception;
    
    /**
     * 从黑名单中移除用户方法（每秒钟限 100 次） 
     * 
     * @param  userId:用户 Id。（必传）
     * @param  blackUserId:被移除的用户Id。（必传）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessResult removeBlacklist() throws Exception;
    
}
