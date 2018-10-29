package io.rong.messages;

import io.rong.util.GsonUtil;

/**
 *
 * 提示条（小灰条）通知消息。此类型消息没有 Push 通知。
 *
 */
public class GrpNtfMessage extends BaseMessage {
    private String operatorUserId;
    private String operation;
    private String data;
	private String message = "";
	private String extra = "";
	private transient static final String TYPE = "RC:GrpNtf";
	
	public static final String Create = "Create";
    public static final String Rename = "Rename";
    public static final String Add = "Add";
    public static final String Kicked = "Kicked";
    public static final String Quit = "Quit";
    public static final String Dismiss = "Dismiss";
	
	public GrpNtfMessage(String operatorUserId, String operation, String data, String message, String extra) {
        super();
        this.operatorUserId = operatorUserId;
        this.operation = operation;
        this.data = data;
        this.message = message;
        this.extra = extra;
    }

    public String getType() {
		return TYPE;
	}
	
	/**
	 * 获取提示条消息内容。
	 *
	 * @returnString
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 设置提示条消息内容。
	 *
	 * @return
	 */
	public void setMessage(String message) {
		this.message = message;
	}  
	
	public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
	 * 获取附加信息(如果开发者自己需要，可以自己在 App 端进行解析)。
	 *
	 * @returnString
	 */
	public String getExtra() {
		return extra;
	}
	
	/**
	 * 设置附加信息(如果开发者自己需要，可以自己在 App 端进行解析)。
	 *
	 * @return
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}  
	
	@Override
	public String toString() {
		return GsonUtil.toJson(this, GrpNtfMessage.class);
	}
}