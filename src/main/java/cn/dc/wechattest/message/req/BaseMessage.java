package cn.dc.wechattest.message.req;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 请求消息基类（普通用户 -> 公众帐号）
 * 
 */
public class BaseMessage {
	// 开发者微信号
	@JsonProperty(value = "ToUserName")
	private String ToUserName;
	// 发送方帐号（一个OpenID）
	@JsonProperty(value = "FromUserName")
	private String FromUserName;
	// 消息创建时间 （整型）
	@JsonProperty(value = "CreateTime")
	private long CreateTime;
	// 消息类型
	@JsonProperty(value = "MsgType")
	private String MsgType;
	// 消息id，64位整型
	@JsonProperty(value = "MsgId")
	private long MsgId;
	// URL
	@JsonProperty(value = "URL")
	private String URL;

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

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

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
}
