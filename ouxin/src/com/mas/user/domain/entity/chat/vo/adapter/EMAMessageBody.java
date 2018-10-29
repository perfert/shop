package com.mas.user.domain.entity.chat.vo.adapter;

/**
 * 好友表
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 * 
 */
public abstract class EMAMessageBody extends EMABase {
    
    public static final int EMAMessageBodyType_TEXT = 0;
    public static final int EMAMessageBodyType_IMAGE = 1;
    public static final int EMAMessageBodyType_VIDEO = 2;
    public static final int EMAMessageBodyType_LOCATION = 3;
    public static final int EMAMessageBodyType_VOICE = 4;
    public static final int EMAMessageBodyType_FILE = 5;
    public static final int EMAMessageBodyType_COMMAND = 6;
    public int type = 0;

    public EMAMessageBody() {
    }

    public int type() {
        return this.nativeType();
    }

    public native int nativeType();
}
