package cn.dc.wechattest.message.resp;
/**
 * 视频消息
 * @author Administrator
 *
 */
public class VideoMessage extends BaseMessage {
	private Video video ;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	
}
