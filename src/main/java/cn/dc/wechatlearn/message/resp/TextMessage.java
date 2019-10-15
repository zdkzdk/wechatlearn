package cn.dc.wechatlearn.message.resp;
/**
 * 文本消息
 * @author Administrator
 *
 */
public class TextMessage extends BaseMessage{
	private String Content;//文本内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
