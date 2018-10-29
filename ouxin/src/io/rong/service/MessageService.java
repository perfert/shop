package io.rong.service;

import org.json.JSONObject;

import io.rong.messages.BaseMessage;
import io.rong.messages.CmdMsgMessage;
import io.rong.models.CodeSuccessResult;
import io.rong.models.MsgObj;
import io.rong.models.Notification;
import io.rong.models.TagObj;

/**    
 *     
 * 项目名称：server-sdk-java-master    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface MessageService {

    /**
     * 发送群组通知消息
     * @param groupIds
     * @param operatorUserId
     * @param operation
     * @param data
     * @param message
     * @param extra
     * @return
     */
    public CodeSuccessResult  publishGrpNtfMessage(String[] groupIds,String operatorUserId,String operation,String data,String message,String extra) throws Exception;
    
    /**
     * 通用命令通知消息好友申请[客户端不保存]
     * @param sendId    发送ID
     * @param targetId  收件ID
     * @param type      type为操作类型
     * @param data      好友添加内容[operation,message]
     * @return
     */
    public CodeSuccessResult  publishCmdMsgMessageFriend(String sendId,String targetId,String type,String data) throws Exception;
    
    /**
     * 发送系统消息方法（一个用户向一个或多个用户发送系统消息，单条消息最大 128k，会话类型为 SYSTEM。每秒钟最多发送 100 条消息，每次最多同时向 100 人发送，如：一次发送 100 人时，示为 100 条消息。） 
     * 
     * @param  fromUserId:发送人用户 Id。（必传）
     * @param  toUserId:接收用户 Id，提供多个本参数可以实现向多人发送消息，上限为 1000 人。（必传）
     * @param  txtMessage:发送消息内容（必传）
     * @param  pushContent:如果为自定义消息，定义显示的 Push 内容，内容中定义标识通过 values 中设置的标识位内容进行替换.如消息类型为自定义不需要 Push 通知，则对应数组传空值即可。（可选）
     * @param  pushData:针对 iOS 平台为 Push 通知时附加到 payload 中，Android 客户端收到推送消息时对应字段名为 pushData。如不需要 Push 功能对应数组传空值即可。（可选）
     * @param  isPersisted:当前版本有新的自定义消息，而老版本没有该自定义消息时，老版本客户端收到消息后是否进行存储，0 表示为不存储、 1 表示为存储，默认为 1 存储消息。（可选）
     * @param  isCounted:当前版本有新的自定义消息，而老版本没有该自定义消息时，老版本客户端收到消息后是否进行未读消息计数，0 表示为不计数、 1 表示为计数，默认为 1 计数，未读消息数增加 1。（可选）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessResult publishSystem(String fromUserId, String[] toUserId, BaseMessage message, String pushContent, String pushData, Integer isPersisted, Integer isCounted) throws Exception;
   
   
    /**
     * 广播消息方法（fromuserid 和 message为null即为不落地的push）
     * @param platform:目标操作系统。（iOS、Android）。（必传）
     * @param fromuserid:发送人用户 Id。（必传）
     * @param audience:推送条件，包括： tag 、 userid 、 is_to_all。（必传）
     * @param message:用于Push中的message。
     * @param notification:按操作系统类型推送消息内容，如 platform 中设置了给 ios 和 android 系统推送消息，而在 notification 中只设置了 ios 的推送内容，则 android 的推送内容为最初 alert 设置的内容。
     * @return
     * @throws Exception
     */
    public CodeSuccessResult broadcastPush(String[] platform, String fromuserid, TagObj audience, MsgObj message, Notification notification) throws Exception;
}
