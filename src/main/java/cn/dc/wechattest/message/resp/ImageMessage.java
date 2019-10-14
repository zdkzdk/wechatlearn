package cn.dc.wechattest.message.resp;
/**
 * 图片消息
 * @author Administrator
 *
 */
public class ImageMessage extends BaseMessage{
	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
