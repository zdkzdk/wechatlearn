package cn.dc.wechattest.message.req;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 文本消息
 * 
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	@JsonProperty(value = "Content")
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "TextMessage{" +
				"Content='" + Content + '\'' +
				'}';
	}
}

