package cn.dc.wechatlearn.message.resp;

/**
 * 响应消息基类
 * @author Administrator
 *
 */
public class BaseMessage {

	private String ToUserName;//接收方账号（一个openid）
	
	private String FromUserName;//开发者微信号
	
	private long CreateTime;//消息创建时间

	private String MsgType;//消息类型

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
}
