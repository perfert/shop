package com.mas.user.domain.entity.chat.vo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.mas.user.domain.entity.chat.vo.adapter.EMABase;
import com.mas.user.domain.entity.chat.vo.adapter.EMAMessageBody;

public class EMAMessage extends EMABase {
    public static final int EMAChatType_SINGLE = 0;
    public static final int EMAChatType_GROUP = 1;
    public static final int EMAChatType_CHATROOM = 2;
    public static final int EMAMessageStatus_NEW = 0;
    public static final int EMAMessageStatus_DELIVERING = 1;
    public static final int EMAMessageStatus_SUCCESS = 2;
    public static final int EMAMessageStatus_FAIL = 3;

    public EMAMessage(EMAMessage var1) {
        this.nativeInit(var1);
    }

    public EMAMessage.EMAMessageStatus _status() {
        int var1 = this.nativeStatus();
        switch(var1) {
        case 0:
            return EMAMessage.EMAMessageStatus.NEW;
        case 1:
            return EMAMessage.EMAMessageStatus.DELIVERING;
        case 2:
            return EMAMessage.EMAMessageStatus.SUCCESS;
        case 3:
            return EMAMessage.EMAMessageStatus.FAIL;
        default:
            return EMAMessage.EMAMessageStatus.FAIL;
        }
    }

    public String msgId() {
        return this.nativeMsgId();
    }

    public String from() {
        return this.nativeFrom();
    }

    public String to() {
        return this.nativeTo();
    }

    public String conversationId() {
        return this.nativeConversationId();
    }

    public void setMsgId(String var1) {
        this.nativeSetMsgId(var1);
    }

    public void setFrom(String var1) {
        this.nativeSetFrom(var1);
    }

    public void setTo(String var1) {
        this.nativeSetTo(var1);
    }

    public void setConversationId(String var1) {
        this.nativeSetConversationId(var1);
    }

    public void setStatus(int var1) {
        this.nativeSetStatus(var1);
    }

    public boolean isRead() {
        return this.nativeIsRead();
    }

    public boolean isAcked() {
        return this.nativeIsAcked();
    }

    public boolean isDeliverAcked() {
        return this.nativeIsDeliverAcked();
    }

    public boolean isOffline() {
        return this.nativeIsOffline();
    }

    public long timeStamp() {
        return this.nativeTimeStamp();
    }

    public void setIsRead(boolean var1) {
        this.nativeSetIsRead(var1);
    }

    public void setIsAcked(boolean var1) {
        this.nativeSetIsAcked(var1);
    }

    public boolean isListened() {
        return this.nativeIsListened();
    }

    native boolean nativeIsListened();

    public void setListened(boolean var1) {
        this.nativeSetListened(var1);
    }

    native void nativeSetListened(boolean var1);

    public void setIsDeliverAcked(boolean var1) {
        this.nativeSetIsDeliverAcked(var1);
    }

    public void setIsOffline(boolean var1) {
        this.nativeSetIsOffline(var1);
    }

    public static EMAMessage createReceiveMessage(String var0, String var1, EMAMessageBody var2, int var3) {
        return nativeCreateReceiveMessage(var0, var1, var2, var3);
    }

    public static EMAMessage createSendMessage(String var0, String var1, EMAMessageBody var2, int var3) {
        return nativeCreateSendMessage(var0, var1, var2, var3);
    }

    public void setAttribute(String var1, int var2) {
        this.nativeSetAttribute(var1, var2);
    }

    public void setAttribute(String var1, long var2) {
        this.nativeSetAttribute(var1, var2);
    }

    public void setAttribute(String var1, String var2) {
        this.nativeSetAttribute(var1, var2);
    }

    public void setAttribute(String var1, boolean var2) {
        this.nativeSetAttribute(var1, var2);
    }

    public void setJsonAttribute(String var1, String var2) {
        this.nativeSetJsonAttribute(var1, var2);
    }

    public boolean getJsonAttribute(String var1, String var2, StringBuilder var3) {
        return this.nativeGetJsonAttribute(var1, var2, var3);
    }

    public boolean getIntAttribute(String var1, int var2, AtomicInteger var3) {
        return this.nativeGetIntAttribute(var1, var2, var3);
    }

    public boolean getLongAttribute(String var1, long var2, AtomicLong var4) {
        return this.nativeGetLongAttribute(var1, var2, var4);
    }

    public boolean getStringAttribute(String var1, String var2, StringBuilder var3) {
        return this.nativeGetStringAttribute(var1, var2, var3);
    }

    public boolean getBooleanAttribute(String var1, boolean var2, AtomicBoolean var3) {
        return this.nativeGetBooleanAttribute(var1, var2, var3);
    }

    public List<EMAMessageBody> bodies() {
        return this.nativeBodies();
    }

    public void addBody(EMAMessageBody var1) {
        this.nativeAddBody(var1);
    }

    public void clearBodies() {
        this.nativeClearBodies();
    }

    public EMAMessage.EMAChatType chatType() {
        int var1 = this.nativeChatType();
        EMAMessage.EMAChatType var2 = EMAMessage.EMAChatType.SINGLE;
        if(var1 == EMAMessage.EMAChatType.SINGLE.ordinal()) {
            var2 = EMAMessage.EMAChatType.SINGLE;
        } else if(var1 == EMAMessage.EMAChatType.GROUP.ordinal()) {
            var2 = EMAMessage.EMAChatType.GROUP;
        } else {
            var2 = EMAMessage.EMAChatType.CHATROOM;
        }

        return var2;
    }

    public void setChatType(EMAMessage.EMAChatType var1) {
        this.nativeSetChatType(var1.ordinal());
    }

    public void setTimeStamp(long var1) {
        this.nativeSetTimeStamp(var1);
    }

    public int progress() {
        return this.nativeProgress();
    }

    public void setProgress(int var1) {
        this.nativeSetProgress(var1);
    }

    public void setCallback(EMCallBack var1) {
        this.nativeSetCallback(var1);
    }

    public EMAMessage.EMADirection direction() {
        int var1 = this.nativeDirection();
        return var1 == EMAMessage.EMADirection.SEND.ordinal()?EMAMessage.EMADirection.SEND:EMAMessage.EMADirection.RECEIVE;
    }

    public void setDirection(int var1) {
        this.nativeSetDirection(var1);
    }

    public long getLocalTime() {
        return this.nativeGetLocalTime();
    }

    public void setLocalTime(long var1) {
        this.nativeSetLocalTime(var1);
    }

    public native String nativeMsgId();

    public native String nativeFrom();

    public native String nativeTo();

    public native String nativeConversationId();

    public native int nativeStatus();

    public native void nativeSetMsgId(String var1);

    public native void nativeSetFrom(String var1);

    public native void nativeSetTo(String var1);

    public native void nativeSetConversationId(String var1);

    public native void nativeSetStatus(int var1);

    public native boolean nativeIsRead();

    public native boolean nativeIsAcked();

    public native boolean nativeIsDeliverAcked();

    public native boolean nativeIsOffline();

    public native long nativeTimeStamp();

    public native void nativeSetIsRead(boolean var1);

    public native void nativeSetIsAcked(boolean var1);

    public native void nativeSetIsDeliverAcked(boolean var1);

    public native void nativeSetIsOffline(boolean var1);

    public static native EMAMessage nativeCreateReceiveMessage(String var0, String var1, EMAMessageBody var2, int var3);

    public static native EMAMessage nativeCreateSendMessage(String var0, String var1, EMAMessageBody var2, int var3);

    public native void nativeSetAttribute(String var1, int var2);

    public native void nativeSetAttribute(String var1, String var2);

    public native void nativeSetAttribute(String var1, boolean var2);

    public native void nativeSetAttribute(String var1, long var2);

    native void nativeSetJsonAttribute(String var1, String var2);

    public native boolean nativeGetIntAttribute(String var1, int var2, AtomicInteger var3);

    public native boolean nativeGetLongAttribute(String var1, long var2, AtomicLong var4);

    public native boolean nativeGetStringAttribute(String var1, String var2, StringBuilder var3);

    public native boolean nativeGetBooleanAttribute(String var1, boolean var2, AtomicBoolean var3);

    native boolean nativeGetJsonAttribute(String var1, String var2, StringBuilder var3);

    public native List<EMAMessageBody> nativeBodies();

    public native void nativeAddBody(EMAMessageBody var1);

    public native void nativeClearBodies();

    native int nativeDirection();

    native void nativeSetDirection(int var1);

    native int nativeChatType();

    native void nativeSetChatType(int var1);

    native void nativeSetTimeStamp(long var1);

    native int nativeProgress();

    native void nativeSetProgress(int var1);

    native void nativeSetCallback(EMCallBack var1);

    native long nativeGetLocalTime();

    native void nativeSetLocalTime(long var1);

    public EMAMessage() {
        this.nativeInit();
    }

    public void finalize() throws Throwable {
        this.nativeFinalize();
        super.finalize();
    }

    native void nativeInit();

    native void nativeInit(EMAMessage var1);

    native void nativeFinalize();

    public static enum EMADirection {
        SEND,
        RECEIVE;

        private EMADirection() {
        }
    }

    public static enum EMAMessageStatus {
        NEW,
        DELIVERING,
        SUCCESS,
        FAIL;

        private EMAMessageStatus() {
        }
    }

    public static enum EMAChatType {
        SINGLE,
        GROUP,
        CHATROOM;

        private EMAChatType() {
        }
    }
}
