/**
 * 融云 Server API java 客户端
 * create by kitName
 * create datetime : 2017-03-13 
 * 
 * v2.0.1
 */
package io.rong;

import java.util.concurrent.ConcurrentHashMap;

import io.rong.methods.*;
import io.rong.service.config.PushConfig;

/**
 *     
 * 项目名称：融云推送    
 * 类名称： 融云推送上下文    
 * 创建人：yixuan  
 * @version v1.00
 *
 */
public class RongCloud {

	private static ConcurrentHashMap<String, RongCloud> rongCloud = new ConcurrentHashMap<String,RongCloud>();
	
	/**
    * getToken  获取 Token 
    * refresh  刷新用户信息
    * checkOnline  检查用户在线状态 
    * block  封禁用户
    * unBlock  解除用户封禁
    * queryBlock  获取被封禁用户
    * addBlacklist  添加用户到黑名单
    * queryBlacklist  获取某用户的黑名单列表
    * removeBlacklist  从黑名单中移除用户
	*/
	public User user;
	/**
	 * publishPrivate  发送单聊消息
	 * publishTemplate  发送单聊模板消息
	 * PublishSystem  发送系统消息
	 * publishSystemTemplate  发送系统模板消息
	 * publishGroup  发送群组消息
	 * publishDiscussion  发送讨论组消息
	 * publishChatroom  发送聊天室消息
	 * broadcast  发送广播消息
	 * getHistory  消息历史记录下载地址获取 消息历史记录下载地址获取。获取 APP 内指定某天某小时内的所有会话消息记录的下载地址
	 * deleteMessage  消息历史记录删除
	 */
	public Message message;
	/**
	 * Wordfilter
	 * 添加敏感词
	 * 移除敏感词
	 * 查询敏感词列表
	 */
	public Wordfilter wordfilter;
	/**
	 * create  创建群组
	 * sync  同步用户所属群组
	 * refresh  刷新群组信息
	 * join  将用户加入指定群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人
	 * queryUser  查询群成员


	 * rollBackGagUser  移除禁言群成员
	 * dismiss  解散群组。
	 */
	public Group group;
	public Chatroom chatroom;
	/**
	 * setUserPushTag  添加 Push 标签
	 * broadcastPush  广播消息
	 */
	public Push push;
	public SMS sms;

	private RongCloud(String appKey, String appSecret) {
		user = new User(appKey, appSecret);
		message = new Message(appKey, appSecret);
		wordfilter = new Wordfilter(appKey, appSecret);
		group = new Group(appKey, appSecret);
		chatroom = new Chatroom(appKey, appSecret);
		push = new Push(appKey, appSecret);
		sms = new SMS(appKey, appSecret);

	}

	public static RongCloud getInstance() {
	    PushConfig pushConfig = PushConfig.getInstance().init();
	    String appKey = pushConfig.getAppKey();
	    String appSecret = pushConfig.getAppSecret();
		if (null == rongCloud.get(appKey)) {
			rongCloud.putIfAbsent(appKey, new RongCloud(appKey, appSecret));
		}
		return rongCloud.get(appKey);
	}
	 
}